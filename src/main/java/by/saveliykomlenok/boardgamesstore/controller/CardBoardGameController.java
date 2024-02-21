package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.cart.CartBoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartBoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.entity.User;
import by.saveliykomlenok.boardgamesstore.service.CartBoardGameService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart-board-games")
@SecurityRequirement(name = "BearerAuth")
public class CardBoardGameController {
    private final CartBoardGameService cartBoardGameService;

    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping
    public List<CartBoardGameReadDto> findAll(Principal principal){
        User user = getCurrentUser(principal);
        return cartBoardGameService.findAll(user);
    }

    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("/{id}")
    public CartBoardGameReadDto findById(@PathVariable("id") Long id){
        return cartBoardGameService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('user:create')")
    @PostMapping
    public CartBoardGameReadDto create(@RequestBody CartBoardGameCreateEditDto cartBoardGameDto, Principal principal){
        User user = getCurrentUser(principal);
        return cartBoardGameService.create(user, cartBoardGameDto);
    }

    @PreAuthorize("hasAnyAuthority('user:update')")
    @PutMapping("/{id}")
    public CartBoardGameReadDto update(@PathVariable("id") Long id, @RequestBody CartBoardGameCreateEditDto cartBoardGameDto, Principal principal){
        User user = getCurrentUser(principal);
        return cartBoardGameService.update(user, id, cartBoardGameDto);
    }

    @PreAuthorize("hasAnyAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        cartBoardGameService.delete(id);
    }

    private User getCurrentUser(Principal principal){
        return (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    }
}
