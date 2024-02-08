package by.saveliykomlenok.boardgamesstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_board_games")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderBoardGames {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_game")
    private BoardGame boardGame;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order")
    private MainOrder mainOrder;
}
