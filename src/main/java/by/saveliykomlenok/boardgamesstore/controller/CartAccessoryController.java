package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.cart.CartAccessoryCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartAccessoryReadDto;
import by.saveliykomlenok.boardgamesstore.service.CartAccessoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart-accessories")
public class CartAccessoryController {
    private final CartAccessoryService cartAccessoryService;

    @GetMapping
    public List<CartAccessoryReadDto> findAll(){
        return cartAccessoryService.findAll(4L);
    }

    @GetMapping("/{id}")
    public CartAccessoryReadDto findById(@PathVariable("id") Long id){
        return cartAccessoryService.findById(id);
    }

    @PostMapping
    public CartAccessoryReadDto create(@RequestBody CartAccessoryCreateEditDto cartAccessoryDto){
        return cartAccessoryService.create(null, cartAccessoryDto);
    }

    @PutMapping("/{id}")
    public CartAccessoryReadDto update(@PathVariable("id") Long id, @RequestBody CartAccessoryCreateEditDto cartAccessoryDto){
        return cartAccessoryService.update(id, cartAccessoryDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        cartAccessoryService.delete(id);
    }
}
