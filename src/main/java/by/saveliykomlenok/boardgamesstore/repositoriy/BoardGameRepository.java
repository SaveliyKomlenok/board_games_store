package by.saveliykomlenok.boardgamesstore.repositoriy;

import by.saveliykomlenok.boardgamesstore.entity.BoardGame;
import by.saveliykomlenok.boardgamesstore.entity.BoardGameType;
import by.saveliykomlenok.boardgamesstore.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {
    Optional<BoardGame> findBoardGameByNameAndPriceAndNumberOfPlayersAndAgeAndAmountAndManufacturerIdAndBoardGamesTypeId(String name,
                                                                                                                     double price,
                                                                                                                     int numberOfPlayers,
                                                                                                                     int age,
                                                                                                                     int amount,
                                                                                                                     Long manufacturer,
                                                                                                                     Long boardGameType);
}
