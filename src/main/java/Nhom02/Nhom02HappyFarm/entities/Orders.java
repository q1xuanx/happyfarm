package Nhom02.Nhom02HappyFarm.entities;

import jakarta.persistence.*;
import jakarta.ws.rs.DefaultValue;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;


import java.sql.Date;
import java.util.List;

@Data
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idOrders;

    private Date orderDate;

    private Date receiveDate;

    private String address;

    @DefaultValue(value = "Đã Đặt")
    private String statusOrders;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUser")
    private Users idUserOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idVoucher", nullable = true)
    private VoucherDiscount idVoucher;
    @OneToMany(mappedBy = "idOrders")
    private List<DetailsOrders> listOrders;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idMethod")
    private PaymentMethod paymentMethod;
}
