package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.order.OrderAccessoryReadDto;
import by.saveliykomlenok.boardgamesstore.service.OrderAccessoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-accessories")
@SecurityRequirement(name = "BearerAuth")
public class OrderAccessoryController {
    private final OrderAccessoryService orderAccessoryService;

    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping("/{id}")
    public List<OrderAccessoryReadDto> findAllById(@PathVariable("id") Long id){
        return orderAccessoryService.findAllById(id);
    }
}
