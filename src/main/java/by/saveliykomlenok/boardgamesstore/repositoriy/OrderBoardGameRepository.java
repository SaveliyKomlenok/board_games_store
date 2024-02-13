package by.saveliykomlenok.boardgamesstore.repositoriy;

import by.saveliykomlenok.boardgamesstore.entity.OrderBoardGames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderBoardGameRepository extends JpaRepository<OrderBoardGames, Long> {
    List<OrderBoardGames> findOrderBoardGamesByMainOrderId(Long orderId);

    void deleteOrderBoardGamesByMainOrderId(Long orderId);
}
