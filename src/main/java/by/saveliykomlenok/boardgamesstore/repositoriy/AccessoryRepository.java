package by.saveliykomlenok.boardgamesstore.repositoriy;

import by.saveliykomlenok.boardgamesstore.entity.Accessory;
import by.saveliykomlenok.boardgamesstore.entity.AccessoryType;
import by.saveliykomlenok.boardgamesstore.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
    Optional<Accessory> findAccessoryByNameAndPriceAndAmountAndManufacturerIdAndAccessoryTypeId(String name,
                                                                                               double price,
                                                                                               int amount,
                                                                                               Long manufacturer,
                                                                                               Long accessoryType);
}
