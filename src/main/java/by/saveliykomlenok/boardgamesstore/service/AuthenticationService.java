package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.auth.AuthenticationDto;
import by.saveliykomlenok.boardgamesstore.dto.user.UserAuthorizeDto;
import by.saveliykomlenok.boardgamesstore.dto.user.UserCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationDto register(UserCreateEditDto userDto) {
        var jwtToken = jwtService.generateToken(userService.create(userDto));
        return AuthenticationDto.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationDto authenticate(UserAuthorizeDto userDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(),
                        userDto.getPassword()
                )
        );
        var user = userService.findByUsername(userDto.getUsername());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationDto.builder()
                .token(jwtToken)
                .build();
    }
}
