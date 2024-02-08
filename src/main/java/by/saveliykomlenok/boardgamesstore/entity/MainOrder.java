package by.saveliykomlenok.boardgamesstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "main_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private String address;

    @OneToMany(mappedBy = "mainOrder")
    private List<OrderBoardGames> orderBoardGames;

    @OneToMany(mappedBy = "mainOrder")
    private List<OrderAccessories> orderAccessories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;
}
