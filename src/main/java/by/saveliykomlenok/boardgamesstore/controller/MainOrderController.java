package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.dto.order.MainOrderCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.order.MainOrderReadDto;
import by.saveliykomlenok.boardgamesstore.service.MainOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main-orders")
public class MainOrderController {
    private final MainOrderService mainOrderService;

    @GetMapping
    public List<MainOrderReadDto> findAll(){
        return mainOrderService.findAll(null);
    }

    @PostMapping
    public MainOrderReadDto create(@RequestBody MainOrderCreateEditDto mainOrderDto){
        return mainOrderService.create(null, mainOrderDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        mainOrderService.delete(id);
    }
}
