package Nhom02.Nhom02HappyFarm.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
//DVT:
@Data
@Entity(name = "Fertilizer")
@Getter
@Setter
@RequiredArgsConstructor
public class Fertilizer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idFertilizer;
    @NotNull(message = "Thiếu tên sản phẩm")
    @NotEmpty(message = "Thiếu tên sản phẩm")
    @Column(name = "NameFertilizer", nullable = false)
    private String nameFertilizer;
    @NotNull(message = "Thiếu giới thiệu sản phẩm")
    @NotEmpty(message = "Thiếu giới thiệu sản phẩm")
    @Column(name = "Description", nullable = false)
    private String description;
    private String thanhPhan;
    private String donViTinh;
    private String url;
    @NotNull(message = "Thiếu giá sản phẩm")
    @Column(name = "Price", nullable = false)
    private float price;
    @NotNull(message = "Thiếu chi tiết sản phẩm")
    @NotEmpty(message = "Thiếu chi tiết sản phẩm")
    @Column(name = "Details", nullable = false)
    private String details;
    @Column(name= "ImageRepresent", nullable = false)
    private String imageRepresent;
    @Column(name= "ImageOptional", nullable = false)
    @ElementCollection
    private List<String> imageOptional;
    @NotNull(message = "Thiếu số lượng sản phẩm")
    @Column(name= "Nums", nullable = false)
    private int nums;
    @Column(name="IsDelete")
    private boolean isDelete;
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
