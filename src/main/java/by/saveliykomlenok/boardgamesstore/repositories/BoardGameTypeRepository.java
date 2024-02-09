package by.saveliykomlenok.boardgamesstore.repositories;

import by.saveliykomlenok.boardgamesstore.entity.BoardGameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardGameTypeRepository extends JpaRepository<BoardGameType, Long> {
}
