package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.cart.CartBoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartBoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.service.CartBoardGameService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart-board-games")
@SecurityRequirement(name = "BearerAuth")
public class CardBoardGameController {
    private final CartBoardGameService cartBoardGameService;

    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping
    public List<CartBoardGameReadDto> findAll(){
        return cartBoardGameService.findAll(4L);
    }

    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("/{id}")
    public CartBoardGameReadDto findById(@PathVariable("id") Long id){
        return cartBoardGameService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('user:create')")
    @PostMapping
    public CartBoardGameReadDto create(@RequestBody CartBoardGameCreateEditDto cartBoardGameDto){
        return cartBoardGameService.create(null, cartBoardGameDto);
    }

    @PreAuthorize("hasAnyAuthority('user:update')")
    @PutMapping("/{id}")
    public CartBoardGameReadDto update(@PathVariable("id") Long id, @RequestBody CartBoardGameCreateEditDto cartBoardGameDto){
        return cartBoardGameService.update(id, cartBoardGameDto);
    }

    @PreAuthorize("hasAnyAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        cartBoardGameService.delete(id);
    }
}
