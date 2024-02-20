package by.saveliykomlenok.boardgamesstore.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReadDto {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private LocalDateTime createdAt;
}
