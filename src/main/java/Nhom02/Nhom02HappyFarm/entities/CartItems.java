package Nhom02.Nhom02HappyFarm.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Data
@Entity
@IdClass(CartItemId.class)
public class CartItems implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idFertilizer")
    private Fertilizer idFertilizer;
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUser")
    private Users users;
    private int quantity;
}
