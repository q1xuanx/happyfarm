package Nhom02.Nhom02HappyFarm.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;

@Data
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idOrders;
    private Date orderDate;
    @Nullable
    private Date receiveDate;
    private String address;
    private String phoneNumber;
    private String statusOrders;
    private float totalAmont;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUser")
    private Users idUserOrder;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idVoucher", nullable = true)
    private VoucherDiscount idVoucher;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idMethod")
    private PaymentMethod paymentMethod;
}
