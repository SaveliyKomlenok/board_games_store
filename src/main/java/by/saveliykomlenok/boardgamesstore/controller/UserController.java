package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.user.UserCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.user.UserReadDto;
import by.saveliykomlenok.boardgamesstore.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@SecurityRequirement(name = "BearerAuth")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('admin:read')")
    @GetMapping
    public List<UserReadDto> findAll(){
        return userService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('admin:read')")
    @GetMapping("/{id}")
    public UserReadDto findById(@PathVariable("id") Long id){
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public UserReadDto update(@PathVariable("id") Long id, @RequestBody UserCreateEditDto userDto){
        return userService.update(id, userDto);
    }

    @PreAuthorize("hasAnyAuthority('admin:delete')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        userService.delete(id);
    }
}
