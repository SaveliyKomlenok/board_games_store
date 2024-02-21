package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.cart.CartAccessoryCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartAccessoryReadDto;
import by.saveliykomlenok.boardgamesstore.entity.User;
import by.saveliykomlenok.boardgamesstore.service.CartAccessoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart-accessories")
@SecurityRequirement(name = "BearerAuth")
public class CartAccessoryController {
    private final CartAccessoryService cartAccessoryService;

    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping
    public List<CartAccessoryReadDto> findAll(Principal principal) {
        User user = getCurrentUser(principal);
        return cartAccessoryService.findAll(user);
    }

    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("/{id}")
    public CartAccessoryReadDto findById(@PathVariable("id") Long id) {
        return cartAccessoryService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('user:create')")
    @PostMapping
    public CartAccessoryReadDto create(@RequestBody CartAccessoryCreateEditDto cartAccessoryDto, Principal principal) {
        User user = getCurrentUser(principal);
        return cartAccessoryService.create(user, cartAccessoryDto);
    }

    @PreAuthorize("hasAnyAuthority('user:update')")
    @PutMapping("/{id}")
    public CartAccessoryReadDto update(@PathVariable("id") Long id, @RequestBody CartAccessoryCreateEditDto cartAccessoryDto, Principal principal) {
        User user = getCurrentUser(principal);
        return cartAccessoryService.update(user, id, cartAccessoryDto);
    }

    @PreAuthorize("hasAnyAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        cartAccessoryService.delete(id);
    }

    private User getCurrentUser(Principal principal) {
        return (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    }
}
