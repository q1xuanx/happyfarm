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
    private String IdOrders;

    private Date OrderDate;

    private Date ReceiveDate;

    @DefaultValue(value = "Đã Đặt")
    private String StatusOrders;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdUser")
    private Users IdUserOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdVoucher")
    private VoucherDiscount IdVoucher;
}
