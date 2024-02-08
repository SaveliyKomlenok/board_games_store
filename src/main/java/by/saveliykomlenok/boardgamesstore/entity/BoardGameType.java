package by.saveliykomlenok.boardgamesstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "board_games_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardGameType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "boardGamesType")
    private List<BoardGame> boardGames;
}
