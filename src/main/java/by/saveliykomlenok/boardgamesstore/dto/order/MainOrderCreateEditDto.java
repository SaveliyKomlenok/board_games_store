package by.saveliykomlenok.boardgamesstore.dto.order;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MainOrderCreateEditDto {
    private LocalDateTime createdAt;
    private String address;
    private Long user;
}
