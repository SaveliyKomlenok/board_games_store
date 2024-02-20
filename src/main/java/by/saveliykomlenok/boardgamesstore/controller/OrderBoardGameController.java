package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.order.OrderBoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.service.OrderBoardGameService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-board-games")
@SecurityRequirement(name = "BearerAuth")
public class OrderBoardGameController {
    private final OrderBoardGameService orderBoardGameService;

    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("/{id}")
    public List<OrderBoardGameReadDto> findAllById(@PathVariable("id") Long id){
        return orderBoardGameService.findAllById(id);
    }
}
