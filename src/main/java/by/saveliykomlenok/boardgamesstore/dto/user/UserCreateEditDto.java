package by.saveliykomlenok.boardgamesstore.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserCreateEditDto {
    private String username;
    private String firstname;
    private String lastname;
    private LocalDateTime createdAt;
}
