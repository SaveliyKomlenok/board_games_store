package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.order.OrderBoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.service.OrderBoardGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-board-games")
public class OrderBoardGameController {
    private final OrderBoardGameService orderBoardGameService;

    @GetMapping("/{id}")
    public List<OrderBoardGameReadDto> findAllById(@PathVariable("id") Long id){
        return orderBoardGameService.findAllById(id);
    }
}
