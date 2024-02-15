package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.order.OrderAccessoryCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.order.OrderAccessoryReadDto;
import by.saveliykomlenok.boardgamesstore.entity.Accessory;
import by.saveliykomlenok.boardgamesstore.entity.MainOrder;
import by.saveliykomlenok.boardgamesstore.entity.OrderAccessories;
import by.saveliykomlenok.boardgamesstore.repositoriy.OrderAccessoryRepository;
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
public class OrderAccessoryService {
    private final OrderAccessoryRepository orderAccessoryRepository;
    private final AccessoryService accessoryService;
    private final ModelMapper mapper;

    public List<OrderAccessoryReadDto> findAllById(Long id) {
        return orderAccessoryRepository.findOrderAccessoriesByMainOrderId(id).stream()
                .map(orderAccessories -> mapper.map(orderAccessories, OrderAccessoryReadDto.class))
                .toList();
    }

    @Transactional
    public OrderAccessoryReadDto create(MainOrder mainOrder, OrderAccessoryCreateEditDto orderAccessoryDto) {
        OrderAccessories orderAccessories = Optional.of(orderAccessoryDto)
                .map(orderAccessoryCreateEditDto -> mapper.map(orderAccessoryDto, OrderAccessories.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE));

        orderAccessories.setAccessory(mapper.map(accessoryService.findById(orderAccessoryDto.getAccessory()), Accessory.class));
        orderAccessories.setMainOrder(mainOrder);
        orderAccessoryRepository.save(orderAccessories);

        return mapper.map(orderAccessories, OrderAccessoryReadDto.class);
    }

    @Transactional
    public void deleteOrderByMainOrderId(Long id) {
        rollbackAmountOfAccessory(id);
        orderAccessoryRepository.deleteOrderAccessoriesByMainOrderId(id);
    }

    private void rollbackAmountOfAccessory(Long id) {
        for (OrderAccessories orderAccessories
                : orderAccessoryRepository.findOrderAccessoriesByMainOrderId(id)) {
            amountRollback(orderAccessories.getAccessory(), orderAccessories.getAmount());
        }
    }

    private void amountRollback(Accessory accessory, int amount) {
        System.err.println(amount);
        AccessoryCreateEditDto accessoryCreateEditDto = AccessoryCreateEditDto.builder()
                .name(accessory.getName())
                .price(accessory.getPrice())
                .amount(accessory.getAmount() + amount)
                .manufacturer(accessory.getManufacturer().getId())
                .accessoryType(accessory.getAccessoryType().getId())
                .build();
        accessoryService.update(accessory.getId(), accessoryCreateEditDto);
    }
}
