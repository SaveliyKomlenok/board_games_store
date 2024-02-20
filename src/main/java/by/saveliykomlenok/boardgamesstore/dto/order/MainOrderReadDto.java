package by.saveliykomlenok.boardgamesstore.dto.order;

import by.saveliykomlenok.boardgamesstore.dto.user.UserReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainOrderReadDto {
    private Long id;
    private LocalDateTime createdAt;
    private String address;
    private UserReadDto user;
}
