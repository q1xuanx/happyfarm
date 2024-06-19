package Nhom02.Nhom02HappyFarm.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@Entity
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idCart;

    private int quantity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="cart_items_fertilizer", joinColumns = @JoinColumn(name = "idCart"), inverseJoinColumns = @JoinColumn(name = "idFertilizer"))
    private List<Fertilizer> idFertilizer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUser")
    private Users users;
}
