package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.service.AuthenticationService;
import by.saveliykomlenok.boardgamesstore.dto.auth.AuthenticationDto;
import by.saveliykomlenok.boardgamesstore.dto.user.UserAuthorizeDto;
import by.saveliykomlenok.boardgamesstore.dto.user.UserCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationDto> register(@RequestBody UserCreateEditDto userDto){
        return ResponseEntity.ok(authenticationService.register(userDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationDto> authenticate(@RequestBody UserAuthorizeDto userDto){
        return ResponseEntity.ok(authenticationService.authenticate(userDto));
    }
}
