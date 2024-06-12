package Nhom02.Nhom02HappyFarm.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idUser;
    private String fullName;

    @Column(name = "DateOfBirth")
    private Date dOB;
    private String password;
    private boolean isBanned;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idRoles")
    private UserRoles roles;
}
