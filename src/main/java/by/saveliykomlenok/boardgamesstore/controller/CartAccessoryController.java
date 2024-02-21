package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.cart.CartAccessoryCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartAccessoryReadDto;
import by.saveliykomlenok.boardgamesstore.entity.User;
import by.saveliykomlenok.boardgamesstore.service.CartAccessoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<CartAccessoryReadDto>> findAll(Principal principal) {
        User user = getCurrentUser(principal);
        return ResponseEntity.ok(cartAccessoryService.findAll(user));
    }

    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("/{id}")
    public ResponseEntity<CartAccessoryReadDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(cartAccessoryService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('user:create')")
    @PostMapping
    public ResponseEntity<CartAccessoryReadDto> create(@RequestBody CartAccessoryCreateEditDto cartAccessoryDto, Principal principal) {
        User user = getCurrentUser(principal);
        return ResponseEntity.ok(cartAccessoryService.create(user, cartAccessoryDto));
    }

    @PreAuthorize("hasAnyAuthority('user:update')")
    @PutMapping("/{id}")
    public ResponseEntity<CartAccessoryReadDto> update(@PathVariable("id") Long id, @RequestBody CartAccessoryCreateEditDto cartAccessoryDto, Principal principal) {
        User user = getCurrentUser(principal);
        return ResponseEntity.ok(cartAccessoryService.update(user, id, cartAccessoryDto));
    }

    @PreAuthorize("hasAnyAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        cartAccessoryService.delete(id);
        return ResponseEntity.ok("Cart accessory removed");
    }

    private User getCurrentUser(Principal principal) {
        return (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    }
}
