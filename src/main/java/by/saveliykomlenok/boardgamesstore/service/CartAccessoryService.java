package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryReadDto;
import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartAccessoryCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartAccessoryReadDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartBoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.entity.*;
import by.saveliykomlenok.boardgamesstore.repositoriy.CartAccessoryRepository;
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public CartAccessoryReadDto create(User user, CartAccessoryCreateEditDto cartAccessoryDto) {
        CartAccessories cartAccessories = Optional.of(cartAccessoryDto)
                .map(cartAccessoryCreateEditDto -> mapper.map(cartAccessoryDto, CartAccessories.class))
                .orElseThrow();
        if (cartAccessoryRepository.existsCartAccessoriesByAccessoryIdAndUserId(cartAccessoryDto.getAccessory(), cartAccessoryDto.getUser())) {
            cartAccessories = cartAccessoryRepository.findCartAccessoriesByAccessoryIdAndUserId(cartAccessoryDto.getAccessory(), cartAccessoryDto.getUser());
            cartAccessories.setAmount(cartAccessories.getAmount() + cartAccessoryDto.getAmount());
        }

        AccessoryReadDto accessoryReadDto = accessoryService.findById(cartAccessoryDto.getAccessory());

        if (isEnoughAmount(accessoryReadDto, cartAccessoryDto)) {
            accessoryReadDto = removeAmountOfAccessory(cartAccessoryDto, accessoryReadDto);
        } else throw new ResponseStatusException(HttpStatus.CONFLICT);

        cartAccessories.setAccessory(mapper.map(accessoryReadDto, Accessory.class));
        cartAccessories.setUser(mapper.map(userService.findById(cartAccessoryDto.getUser()), User.class));
        //cartBoardGames.setUser(user); Реализовать после JWT

        cartAccessoryRepository.saveAndFlush(cartAccessories);
        return mapper.map(cartAccessories, CartAccessoryReadDto.class);
    }

    private AccessoryReadDto removeAmountOfAccessory(CartAccessoryCreateEditDto cartAccessoryCreateEditDto, AccessoryReadDto accessoryReadDto) {
        AccessoryCreateEditDto accessoryCreateEditDto = AccessoryCreateEditDto.builder()
                .name(accessoryReadDto.getName())
                .price(accessoryReadDto.getPrice())
                .amount(accessoryReadDto.getAmount() - cartAccessoryCreateEditDto.getAmount())
                .manufacturer(accessoryReadDto.getManufacturer().getId())
                .accessoryType(accessoryReadDto.getAccessoryType().getId())
                .build();
        return accessoryService.update(accessoryReadDto.getId(), accessoryCreateEditDto);
    }

    public boolean isEnoughAmount(AccessoryReadDto accessoryReadDto, CartAccessoryCreateEditDto cartAccessoryDto) {
        return (accessoryReadDto.getAmount() - cartAccessoryDto.getAmount()) >= 0;
    }

    @Transactional
    public CartAccessoryReadDto update(Long id, CartAccessoryCreateEditDto cartAccessoryDto) {
        CartAccessories cartAccessories = cartAccessoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        mapper.map(cartAccessoryDto, cartAccessories);

        cartAccessories.setAccessory(mapper.map(accessoryService.findById(cartAccessoryDto.getAccessory()), Accessory.class));
        cartAccessories.setUser(mapper.map(userService.findById(cartAccessoryDto.getUser()), User.class));

        cartAccessoryRepository.saveAndFlush(cartAccessories);

        return mapper.map(cartAccessories, CartAccessoryReadDto.class);
    }

    @Transactional
    public void delete(Long id) {
        rollbackAmountOfAccessory(id);
        cartAccessoryRepository.findById(id)
                .map(cartAccessories -> {
                    cartAccessoryRepository.delete(cartAccessories);
                    cartAccessoryRepository.flush();
                    return true;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private void rollbackAmountOfAccessory(Long id) {
        CartAccessories cartAccessories = cartAccessoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        amountRollback(cartAccessories);
    }

    public void amountRollback(CartAccessories cartAccessories) {
        rollback(cartAccessories.getAccessory(), cartAccessories.getAmount(),  accessoryService);
    }

    static void rollback(Accessory accessory, int amount, AccessoryService accessoryService) {
        AccessoryCreateEditDto accessoryCreateEditDto = AccessoryCreateEditDto.builder()
                .name(accessory.getName())
                .price(accessory.getPrice())
                .amount(accessory.getAmount() + amount)
                .manufacturer(accessory.getManufacturer().getId())
                .accessoryType(accessory.getAccessoryType().getId())
                .build();
        accessoryService.update(accessory.getId(), accessoryCreateEditDto);
    }

    @Transactional
    public void deleteCartByUserId(Long id) {
        cartAccessoryRepository.deleteAllByUserId(id);
    }
}
