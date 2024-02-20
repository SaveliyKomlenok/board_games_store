package by.saveliykomlenok.boardgamesstore.dto.manufacturer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerReadDto {
    private Long id;
    private String name;
}
