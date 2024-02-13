package by.saveliykomlenok.boardgamesstore.repositoriy;

import by.saveliykomlenok.boardgamesstore.entity.OrderAccessories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderAccessoryRepository extends JpaRepository<OrderAccessories, Long> {
    List<OrderAccessories> findOrderAccessoriesByMainOrderId(Long orderId);

    void deleteOrderAccessoriesByMainOrderId(Long Id);
}
