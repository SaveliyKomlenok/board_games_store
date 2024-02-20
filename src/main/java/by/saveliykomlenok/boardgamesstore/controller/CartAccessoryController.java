package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.cart.CartAccessoryCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartAccessoryReadDto;
import by.saveliykomlenok.boardgamesstore.service.CartAccessoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart-accessories")
@SecurityRequirement(name = "BearerAuth")
public class CartAccessoryController {
    private final CartAccessoryService cartAccessoryService;

    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping
    public List<CartAccessoryReadDto> findAll(){
        return cartAccessoryService.findAll(4L);
    }

    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("/{id}")
    public CartAccessoryReadDto findById(@PathVariable("id") Long id){
        return cartAccessoryService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('user:create')")
    @PostMapping
    public CartAccessoryReadDto create(@RequestBody CartAccessoryCreateEditDto cartAccessoryDto){
        return cartAccessoryService.create(null, cartAccessoryDto);
    }

    @PreAuthorize("hasAnyAuthority('user:update')")
    @PutMapping("/{id}")
    public CartAccessoryReadDto update(@PathVariable("id") Long id, @RequestBody CartAccessoryCreateEditDto cartAccessoryDto){
        return cartAccessoryService.update(id, cartAccessoryDto);
    }

    @PreAuthorize("hasAnyAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        cartAccessoryService.delete(id);
    }
}
