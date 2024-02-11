package by.saveliykomlenok.boardgamesstore.repositories;

import by.saveliykomlenok.boardgamesstore.entity.CartBoardGames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartBoardGameRepository extends JpaRepository<CartBoardGames, Long> {
    List<CartBoardGames> findCartBoardGamesByUserId(Long id);

    boolean existsCartBoardGamesByBoardGameIdAndUserId(Long boardGameId, Long userId);

    CartBoardGames findCartBoardGamesByBoardGameIdAndUserId(Long boardGameId, Long userId);
}
