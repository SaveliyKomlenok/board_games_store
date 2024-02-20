package by.saveliykomlenok.boardgamesstore.dto.accessory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessoryTypeCreateEditDto {
    private String name;
}
