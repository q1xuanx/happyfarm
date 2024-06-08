package Nhom02.Nhom02HappyFarm.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class VoucherDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idVoucher;
    private String codeVoucher;
    private int discountPercent;
}
