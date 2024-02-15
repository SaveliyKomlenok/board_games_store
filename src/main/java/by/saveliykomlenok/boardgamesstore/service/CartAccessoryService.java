package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryReadDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartAccessoryCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartAccessoryReadDto;
import by.saveliykomlenok.boardgamesstore.entity.Accessory;
import by.saveliykomlenok.boardgamesstore.entity.CartAccessories;
import by.saveliykomlenok.boardgamesstore.entity.User;
import by.saveliykomlenok.boardgamesstore.repositoriy.CartAccessoryRepository;
import by.saveliykomlenok.boardgamesstore.util.exception.accessory.AmountOfAccessoryExceededException;
import by.saveliykomlenok.boardgamesstore.util.exception.cart.CartAccessoryMissingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartAccessoryService {
    private final CartAccessoryRepository cartAccessoryRepository;
    private final AccessoryService accessoryService;
    private final UserService userService;
    private final ModelMapper mapper;

    public List<CartAccessoryReadDto> findAll(Long id) {
//        return cartAccessoryRepository.findAll().stream()
//                .map(cartAccessories -> mapper.map(cartAccessories, CartAccessoryReadDto.class))
//                .toList();
        return cartAccessoryRepository.findCartAccessoriesByUserId(id).stream()
                .map(cartAccessories -> mapper.map(cartAccessories, CartAccessoryReadDto.class))
                .toList();
    }

    public CartAccessoryReadDto findById(Long id) {
        return cartAccessoryRepository.findById(id)
                .map(cartAccessories -> mapper.map(cartAccessories, CartAccessoryReadDto.class))
                .orElseThrow(() -> new CartAccessoryMissingException("Card accessory doesn't exist"));
    }

    @Transactional
    public CartAccessoryReadDto create(User user, CartAccessoryCreateEditDto cartAccessoryDto) {
        CartAccessories cartAccessories = Optional.of(cartAccessoryDto)
                .map(cartAccessoryCreateEditDto -> mapper.map(cartAccessoryDto, CartAccessories.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE));
        if (cartAccessoryRepository.existsCartAccessoriesByAccessoryIdAndUserId(cartAccessoryDto.getAccessory(), cartAccessoryDto.getUser())) {
            cartAccessories = cartAccessoryRepository.findCartAccessoriesByAccessoryIdAndUserId(cartAccessoryDto.getAccessory(), cartAccessoryDto.getUser());
            cartAccessories.setAmount(cartAccessories.getAmount() + cartAccessoryDto.getAmount());
        }

        AccessoryReadDto accessoryReadDto = accessoryService.findById(cartAccessoryDto.getAccessory());

        if (!isEnoughAmount(accessoryReadDto, cartAccessories)) {
            throw new AmountOfAccessoryExceededException("Amount of accessory to purchase has been exceeded");
        }

        cartAccessories.setAccessory(mapper.map(accessoryReadDto, Accessory.class));
        cartAccessories.setUser(mapper.map(userService.findById(cartAccessoryDto.getUser()), User.class));
        //cartAccessories.setUser(user); Реализовать после JWT

        cartAccessoryRepository.saveAndFlush(cartAccessories);
        return mapper.map(cartAccessories, CartAccessoryReadDto.class);
    }

    public boolean isEnoughAmount(AccessoryReadDto accessoryReadDto, CartAccessories cartAccessories) {
        return cartAccessories.getAmount() <= accessoryReadDto.getAmount();
    }

    @Transactional
    public CartAccessoryReadDto update(Long id, CartAccessoryCreateEditDto cartAccessoryDto) {
        CartAccessories cartAccessories = cartAccessoryRepository.findById(id)
                .orElseThrow(() -> new CartAccessoryMissingException("Card accessory doesn't exist"));

        mapper.map(cartAccessoryDto, cartAccessories);

        cartAccessories.setAccessory(mapper.map(accessoryService.findById(cartAccessoryDto.getAccessory()), Accessory.class));
        cartAccessories.setUser(mapper.map(userService.findById(cartAccessoryDto.getUser()), User.class));

        cartAccessoryRepository.saveAndFlush(cartAccessories);

        return mapper.map(cartAccessories, CartAccessoryReadDto.class);
    }

    @Transactional
    public void delete(Long id) {
        cartAccessoryRepository.findById(id)
                .map(cartAccessories -> {
                    cartAccessoryRepository.delete(cartAccessories);
                    cartAccessoryRepository.flush();
                    return true;
                }).orElseThrow(() -> new CartAccessoryMissingException("Card accessory doesn't exist"));
    }

    @Transactional
    public void deleteCartByUserId(Long id) {
        cartAccessoryRepository.deleteAllByUserId(id);
    }
}
