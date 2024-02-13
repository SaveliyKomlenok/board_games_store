package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.cart.CartBoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartBoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.service.CartBoardGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart-board-games")
public class CardBoardGameController {
    private final CartBoardGameService cartBoardGameService;

    @GetMapping
    public List<CartBoardGameReadDto> findAll(){
        return cartBoardGameService.findAll(4L);
    }

    @GetMapping("/{id}")
    public CartBoardGameReadDto findById(@PathVariable("id") Long id){
        return cartBoardGameService.findById(id);
    }

    @PostMapping
    public CartBoardGameReadDto create(@RequestBody CartBoardGameCreateEditDto cartBoardGameDto){
        return cartBoardGameService.create(null, cartBoardGameDto);
    }

    @PutMapping("/{id}")
    public CartBoardGameReadDto update(@PathVariable("id") Long id, @RequestBody CartBoardGameCreateEditDto cartBoardGameDto){
        return cartBoardGameService.update(id, cartBoardGameDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        cartBoardGameService.delete(id);
    }
}
