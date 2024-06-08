package Nhom02.Nhom02HappyFarm.entities;

import jakarta.persistence.*;
import jakarta.ws.rs.DefaultValue;
import lombok.Data;


import java.sql.Date;

@Data
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idOrders;

    private Date orderDate;

    private Date receiveDate;

    @DefaultValue(value = "Đã Đặt")
    private String statusOrders;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUser")
    private Users idUserOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idVoucher")
    private VoucherDiscount idVoucher;
}
