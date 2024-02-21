package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.order.MainOrderCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.order.MainOrderReadDto;
import by.saveliykomlenok.boardgamesstore.entity.User;
import by.saveliykomlenok.boardgamesstore.service.MainOrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main-orders")
@SecurityRequirement(name = "BearerAuth")
public class MainOrderController {
    private final MainOrderService mainOrderService;

    @PreAuthorize("hasAnyAuthority('user:read')")
    @GetMapping
    public List<MainOrderReadDto> findAll(Principal principal){
        User user = getCurrentUser(principal);
        return mainOrderService.findAll(user);
    }

    @PreAuthorize("hasAnyAuthority('user:create')")
    @PostMapping
    public MainOrderReadDto create(@RequestBody MainOrderCreateEditDto mainOrderDto, Principal principal){
        User user = getCurrentUser(principal);
        return mainOrderService.create(user, mainOrderDto);
    }

    @PreAuthorize("hasAnyAuthority('user:delete')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        mainOrderService.delete(id);
    }

    private User getCurrentUser(Principal principal){
        return (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
    }
}
