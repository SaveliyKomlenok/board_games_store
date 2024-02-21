package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.cart.CartAccessoryReadDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartBoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.dto.order.MainOrderCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.order.MainOrderReadDto;
import by.saveliykomlenok.boardgamesstore.dto.order.OrderAccessoryCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.order.OrderBoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.entity.MainOrder;
import by.saveliykomlenok.boardgamesstore.entity.User;
import by.saveliykomlenok.boardgamesstore.repositoriy.MainOrderRepository;
import by.saveliykomlenok.boardgamesstore.util.exception.order.OrderMissingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainOrderService {
    private final MainOrderRepository mainOrderRepository;
    private final CartBoardGameService cartBoardGameService;
    private final CartAccessoryService cartAccessoryService;
    private final OrderBoardGameService orderBoardGameService;
    private final OrderAccessoryService orderAccessoryService;
    private final ModelMapper mapper;

    public List<MainOrderReadDto> findAll(User user) {
        return mainOrderRepository.findMainOrdersByUserId(user.getId()).stream()
                .map(mainOrder -> mapper.map(mainOrder, MainOrderReadDto.class))
                .toList();
    }

    @Transactional
    public MainOrderReadDto create(User user, MainOrderCreateEditDto mainOrderDto) {
        MainOrder mainOrder = Optional.of(mainOrderDto)
                .map(mainOrderCreateEditDto -> mapper.map(mainOrderDto, MainOrder.class))
                .orElseThrow();
        mainOrder.setUser(user);

        MainOrder savedMainOrder = mainOrderRepository.save(mainOrder);

        mapCartToOrderBoardGame(savedMainOrder);
        mapCartToOrderAccessory(savedMainOrder);

        return mapper.map(savedMainOrder, MainOrderReadDto.class);
    }

    private void mapCartToOrderBoardGame(MainOrder mainOrder) {
        for (CartBoardGameReadDto cartBoardGameReadDto : cartBoardGameService.findAll(mainOrder.getUser())) {
            OrderBoardGameCreateEditDto orderBoardGameDto = OrderBoardGameCreateEditDto.builder()
                    .amount(cartBoardGameReadDto.getAmount())
                    .boardGame(cartBoardGameReadDto.getBoardGame().getId())
                    .mainOrder(mainOrder.getId())
                    .build();
            orderBoardGameService.create(mainOrder, orderBoardGameDto);
        }
        cartBoardGameService.deleteCartByUserId(mainOrder.getUser().getId());
    }

    private void mapCartToOrderAccessory(MainOrder mainOrder) {
        for (CartAccessoryReadDto cartAccessoryReadDto : cartAccessoryService.findAll(mainOrder.getUser())) {
            OrderAccessoryCreateEditDto orderAccessoryDto = OrderAccessoryCreateEditDto.builder()
                    .amount(cartAccessoryReadDto.getAmount())
                    .accessory(cartAccessoryReadDto.getAccessory().getId())
                    .mainOrder(mainOrder.getId())
                    .build();
            orderAccessoryService.create(mainOrder, orderAccessoryDto);
        }
        cartAccessoryService.deleteCartByUserId(mainOrder.getUser().getId());
    }

    @Transactional
    public void delete(Long id) {
        mainOrderRepository.findById(id)
                .map(mainOrder -> {
                    orderBoardGameService.deleteOrderByMainOrderId(id);
                    orderAccessoryService.deleteOrderByMainOrderId(id);
                    mainOrderRepository.delete(mainOrder);
                    mainOrderRepository.flush();
                    return true;
                }).orElseThrow(() -> new OrderMissingException("Order doesn't exist"));
    }
}
