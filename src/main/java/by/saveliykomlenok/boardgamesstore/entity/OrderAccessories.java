package by.saveliykomlenok.boardgamesstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_accessories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderAccessories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accessory")
    private Accessory accessory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order")
    private MainOrder mainOrder;
}
