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

    @ManyToOne
    @JoinColumn(name = "accessory")
    private Accessory accessory;

    @ManyToOne
    @JoinColumn(name = "order_obj")
    private MainOrder mainOrder;
}
