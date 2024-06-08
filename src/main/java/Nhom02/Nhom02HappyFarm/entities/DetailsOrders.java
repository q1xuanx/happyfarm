package Nhom02.Nhom02HappyFarm.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DetailsOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String IdDetailsOrders;
    private int Quantity;
    @JoinColumn(name="IdFertilizer")
    @ManyToOne(fetch = FetchType.EAGER)
    private Fertilizer IdFertilizer;
    @JoinColumn(name = "IdOrder")
    @ManyToOne(fetch = FetchType.EAGER)
    private Orders IdOrders;
}
