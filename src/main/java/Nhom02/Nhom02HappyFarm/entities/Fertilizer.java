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
    private String IdFertilizer;
    @Column(name = "NameFertilizer", nullable = false)
    private String NameFertilizer;
    @Column(name = "Description", nullable = false)
    private String Description;
    @Column(name = "Price", nullable = false)
    private float Price;
    @Column(name = "Details", nullable = false)
    private String Details;
    @Column(name= "ImageRepresent", nullable = false)
    private String ImageRepresent;
    @Column(name= "ImageOptional", nullable = false)
    @ElementCollection
    private List<String> ImageOptional;
    @Column(name="IsDelete")
    private Boolean IsDelete;
    //Xuất xứ
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdOrigin")
    private OriginFertilizer OriginFer;
    //Loại phân bón
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdTypeFertilizer")
    private TypeFertilizer Type;
    //Brand
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdBrand")
    private Brand BrandName;
    //Image File Save
    @Transient
    private MultipartFile FileImageRepresent;
    @Transient
    private MultipartFile[] FileImageOptional;
}
