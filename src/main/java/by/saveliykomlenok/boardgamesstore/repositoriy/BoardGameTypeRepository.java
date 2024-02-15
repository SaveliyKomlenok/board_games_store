package by.saveliykomlenok.boardgamesstore.repositoriy;

import by.saveliykomlenok.boardgamesstore.entity.BoardGameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardGameTypeRepository extends JpaRepository<BoardGameType, Long> {
    Optional<BoardGameType> findBoardGameTypeByName(String name);
}
