package by.saveliykomlenok.boardgamesstore.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserReadDto {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private LocalDateTime createdAt;
}
