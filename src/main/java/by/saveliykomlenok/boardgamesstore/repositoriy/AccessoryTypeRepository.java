package by.saveliykomlenok.boardgamesstore.repositoriy;

import by.saveliykomlenok.boardgamesstore.entity.AccessoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessoryTypeRepository extends JpaRepository<AccessoryType, Long> {
}
