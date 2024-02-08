package by.saveliykomlenok.boardgamesstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String firstname;

    private String lastname;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<MainOrder> mainOrders;

    @OneToMany(mappedBy = "user")
    private List<CartBoardGames> cartBoardGames;

    @OneToMany(mappedBy = "user")
    private List<CartAccessories> cartAccessories;
}