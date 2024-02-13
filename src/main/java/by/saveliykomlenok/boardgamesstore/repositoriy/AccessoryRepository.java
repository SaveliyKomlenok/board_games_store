package by.saveliykomlenok.boardgamesstore.repositoriy;

import by.saveliykomlenok.boardgamesstore.entity.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
}
