package Nhom02.Nhom02HappyFarm.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String IdUser;
    private String FullName;
    @Column(name = "DateOfBirth")
    private Date DOB;
    private String Password;
    private boolean IsBanned;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdRoles")
    private UserRoles Roles;
}
