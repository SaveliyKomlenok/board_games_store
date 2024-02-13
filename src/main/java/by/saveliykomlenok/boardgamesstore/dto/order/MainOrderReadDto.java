package by.saveliykomlenok.boardgamesstore.dto.order;

import by.saveliykomlenok.boardgamesstore.dto.user.UserReadDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MainOrderReadDto {
    private Long id;
    private LocalDateTime createdAt;
    private String address;
    private UserReadDto user;
}
