package by.saveliykomlenok.boardgamesstore.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainOrderCreateEditDto {
    private LocalDateTime createdAt;
    private String address;
    private Long user;
}
