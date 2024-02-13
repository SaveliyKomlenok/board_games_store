package by.saveliykomlenok.boardgamesstore.repositoriy;

import by.saveliykomlenok.boardgamesstore.entity.CartAccessories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartAccessoryRepository extends JpaRepository<CartAccessories, Long> {
    List<CartAccessories> findCartAccessoriesByUserId(Long id);

    boolean existsCartAccessoriesByAccessoryIdAndUserId(Long accessoryId, Long userId);

    CartAccessories findCartAccessoriesByAccessoryIdAndUserId(Long accessoryId, Long userId);

    void deleteAllByUserId(Long id);
}
