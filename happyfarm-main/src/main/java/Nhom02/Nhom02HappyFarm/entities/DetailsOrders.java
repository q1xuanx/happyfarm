package Nhom02.Nhom02HappyFarm.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DetailsOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idDetailsOrders;
    private int quantity;
    private String nameFertilizer;
    private float priceFertilizer;
    private String donViTinh;
    @JoinColumn(name = "idOrder")
    @ManyToOne(fetch = FetchType.EAGER)
    private Orders idOrders;
}
