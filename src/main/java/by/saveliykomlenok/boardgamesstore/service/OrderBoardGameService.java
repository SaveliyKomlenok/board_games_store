package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.order.OrderBoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.order.OrderBoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.entity.BoardGame;
import by.saveliykomlenok.boardgamesstore.entity.MainOrder;
import by.saveliykomlenok.boardgamesstore.entity.OrderBoardGames;
import by.saveliykomlenok.boardgamesstore.repositoriy.OrderBoardGameRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderBoardGameService {
    private final OrderBoardGameRepository orderBoardGameRepository;
    private final BoardGameService boardGameService;
    private final ModelMapper mapper;

    public List<OrderBoardGameReadDto> findAllById(Long id) {
        return orderBoardGameRepository.findOrderBoardGamesByMainOrderId(id).stream()
                .map(orderBoardGames -> mapper.map(orderBoardGames, OrderBoardGameReadDto.class))
                .toList();
    }

    @Transactional
    public OrderBoardGameReadDto create(MainOrder mainOrder, OrderBoardGameCreateEditDto orderBoardGameDto) {
        OrderBoardGames orderBoardGames = Optional.of(orderBoardGameDto)
                .map(orderBoardGameCreateEditDto -> mapper.map(orderBoardGameDto, OrderBoardGames.class))
                .orElseThrow();

        orderBoardGames.setBoardGame(mapper.map(boardGameService.findById(orderBoardGameDto.getBoardGame()), BoardGame.class));
        orderBoardGames.setMainOrder(mainOrder);

        orderBoardGameRepository.save(orderBoardGames);

        return mapper.map(orderBoardGames, OrderBoardGameReadDto.class);
    }

    @Transactional
    public void deleteOrderByMainOrderId(Long id) {
        rollbackAmountOfBoardGame(id);
        orderBoardGameRepository.deleteOrderBoardGamesByMainOrderId(id);
    }

    private void rollbackAmountOfBoardGame(Long id) {
        for (OrderBoardGames orderBoardGames
                : orderBoardGameRepository.findOrderBoardGamesByMainOrderId(id)) {
            amountRollback(orderBoardGames);
        }
    }

    private void amountRollback(OrderBoardGames orderBoardGames) {
        CartBoardGameService.rollback(orderBoardGames.getBoardGame(), orderBoardGames.getAmount(), boardGameService);
    }
}
