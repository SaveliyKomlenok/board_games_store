package by.saveliykomlenok.boardgamesstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "accessories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Accessory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer")
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type")
    private AccessoryType accessoryType;

    @OneToMany(mappedBy = "accessory")
    private List<OrderAccessories> orderAccessories;

    @OneToMany(mappedBy = "accessory")
    private List<CartAccessories> cartAccessories;
}
