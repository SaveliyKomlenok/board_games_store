package by.saveliykomlenok.boardgamesstore.repositories;

import by.saveliykomlenok.boardgamesstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
