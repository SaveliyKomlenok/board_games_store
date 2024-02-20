package by.saveliykomlenok.boardgamesstore.dto.boardgame;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardGameTypeReadDto {
    private Long id;
    private String name;
}