package Nhom02.Nhom02HappyFarm.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Entity(name = "Fertilizer")
@Getter
@Setter
@RequiredArgsConstructor
public class Fertilizer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idFertilizer;
    @Column(name = "NameFertilizer", nullable = false)
    private String nameFertilizer;
    @Column(name = "Description", nullable = false)
    private String description;
    @Column(name = "Price", nullable = false)
    private float price;
    @Column(name = "Details", nullable = false)
    private String details;
    @Column(name= "ImageRepresent", nullable = false)
    private String imageRepresent;
    @Column(name= "ImageOptional", nullable = false)
    @ElementCollection
    private List<String> imageOptional;
    @Column(name="IsDelete")
    private Boolean isDelete;
    //Xuất xứ
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idOrigin")
    private OriginFertilizer originFer;
    //Loại phân bón
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idTypeFertilizer")
    private TypeFertilizer type;
    //Brand
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idBrand")
    private Brand brandName;
    //Image File Save
    @Transient
    private MultipartFile fileImageRepresent;
    @Transient
    private MultipartFile[] fileImageOptional;
}
