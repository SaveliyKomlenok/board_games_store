package by.saveliykomlenok.boardgamesstore.repositoriy;

import by.saveliykomlenok.boardgamesstore.dto.order.MainOrderReadDto;
import by.saveliykomlenok.boardgamesstore.entity.MainOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainOrderRepository extends JpaRepository<MainOrder, Long> {
    List<MainOrder> findMainOrdersByUserId(Long id);
}
