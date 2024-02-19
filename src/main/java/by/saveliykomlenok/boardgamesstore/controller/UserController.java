package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.user.UserCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.user.UserReadDto;
import by.saveliykomlenok.boardgamesstore.service.UserService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@SecurityRequirement(name = "BearerAuth")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserReadDto> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserReadDto findById(@PathVariable("id") Long id){
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public UserReadDto update(@PathVariable("id") Long id, @RequestBody UserCreateEditDto userDto){
        return userService.update(id, userDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        userService.delete(id);
    }
}
