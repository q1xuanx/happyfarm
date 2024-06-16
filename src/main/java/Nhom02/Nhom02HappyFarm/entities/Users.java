package Nhom02.Nhom02HappyFarm.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idUser;
    private String username;
    private String fullName;
    private String email;

    @Column(name = "DateOfBirth")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dob;

    private String password;

    private boolean isBanned;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idRoles")
    private UserRoles roles;
}
