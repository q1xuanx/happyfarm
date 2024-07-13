package Nhom02.Nhom02HappyFarm.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@RequiredArgsConstructor
@Embeddable
public class CartItemId implements Serializable {
    private String idFertilizer;
    private String users;
}
