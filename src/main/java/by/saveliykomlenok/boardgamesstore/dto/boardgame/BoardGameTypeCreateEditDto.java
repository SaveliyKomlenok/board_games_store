package by.saveliykomlenok.boardgamesstore.dto.boardgame;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardGameTypeCreateEditDto {
    private String name;
}
