package by.saveliykomlenok.boardgamesstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "board_games")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    private int numberOfPlayers;

    private int age;

    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer")
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type")
    private BoardGameType boardGamesType;

    @OneToMany(mappedBy = "boardGame")
    private List<OrderBoardGames> orderBoardGames;

    @OneToMany(mappedBy = "boardGame")
    private List<CartBoardGames> cartBoardGames;
}
