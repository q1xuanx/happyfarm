package Nhom02.Nhom02HappyFarm.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idContact;
    private String email;
    private String content;
    private String name;
    private String phoneNumber;
    private String dateSend;
}

