package Nhom02.Nhom02HappyFarm.api.brand;


import Nhom02.Nhom02HappyFarm.entities.Brand;
import Nhom02.Nhom02HappyFarm.entities.OriginFertilizer;
import Nhom02.Nhom02HappyFarm.service.BrandService;
import Nhom02.Nhom02HappyFarm.service.OriginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
@Api(value = "Manage Brand Of Fertilizer")
public class BrandApi {
    private final BrandService brandService;
    @ApiOperation(value = "Tra ve 1 list cac thuong hieu bao gom ca xoa va chua xoa")
    @ApiResponses(value ={
        @ApiResponse(code = 200, message = "Tra ve thanh cong"),
        @ApiResponse(code = 204, message = "List rong"), @ApiResponse(code = 400, message = "Lỗi khi lấy list")
    })
    @GetMapping("/getlistbrand")
    public ResponseEntity<List<Brand>> getListBrand(@RequestParam(required = false) String nameBrand){
        if (brandService.GetAllBrand(nameBrand).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(brandService.GetAllBrand(nameBrand), HttpStatus.OK);
    }
    @ApiOperation(value = "Thêm mới 1 brand vào db")
    @ApiResponses(value ={
            @ApiResponse(code = 201, message = "Tạo mới thành công"),
            @ApiResponse(code = 401, message = "Có lỗi ve du lieu trong quá trình thêm, kiểm tra xem có dữ liệu nào null hay không")
    })
    @PostMapping("/addnew")
    public ResponseEntity<Brand> createNewBrand(@RequestBody Brand brand){
        try{
            brandService.AddOrEditBrand(brand);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception error) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @ApiOperation(value = "Tìm 1 brand với tên trong DB (ten phải nhập đúng thì mới trả ra kết quả)")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Kết quả trả về thành công"),
            @ApiResponse(code = 401, message = "Có lỗi ve du lieu trong quá trình thêm, kiểm tra xem có dữ liệu nào null hay không")
    })
    @GetMapping("/find/{name}")
    public ResponseEntity<List<Brand>> findByName(@PathVariable(name = "name") String name){
        List<Brand> list = brandService.findByName(name);
        if(list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Lay 1 brand")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tao 1 brand thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay du lieu")
    })
    @GetMapping("/newBrand")
    public ResponseEntity<Brand> createNew(){
        try{
            return new ResponseEntity<>(new Brand(), HttpStatus.OK);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation(value = "Lay 1 brand bang id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lay thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay du lieu")
    })
    @GetMapping("/getbrand/{id}")
    public ResponseEntity<Brand> getBrand(@PathVariable String id){
        try{
            Brand brand = brandService.GetBrand(id);
            return new ResponseEntity<>(brand, HttpStatus.OK);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation(value = "Chỉnh sua brand trong DB")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Chinh sua thanh cong"),
            @ApiResponse(code = 404, message = "Không tìm thay Id cua brand cần chỉnh sửa")
    })
    @PutMapping("/editbrand/{id}")

    public ResponseEntity<Brand> editBrand(@PathVariable(name = "id") String idBrand, @RequestBody Brand brand){
        Brand getBrand = brandService.GetBrand(idBrand);
        if (getBrand == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            brandService.AddOrEditBrand(brand);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    @ApiOperation(value = "Xóa 1 brand với ID là tham số truyền vào")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Thành công"),
            @ApiResponse(code = 400, message = "Không tìm thay Id cua brand cần xoa")
    })
    @DeleteMapping("/deletebrand/{id}")
    public ResponseEntity<Brand> deteleBrand(@PathVariable(name = "id") String idBrand){
        try {
            brandService.DeleteBrand(idBrand);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @ApiOperation(value = "Trả về các brand với IsDelete = false (Trả ve cac brand chưa xóa)")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Thành công"),
            @ApiResponse(code = 400, message = "Không tìm thay Id cua brand cần xoa"),
            @ApiResponse(code = 204, message = "List rỗng"),
    })
    @GetMapping("/notdeletebrand")
    public ResponseEntity<List<Brand>> brandIsNotDelete(){
        List<Brand> listBrand = brandService.GetAllOriginNotDelete();
        if (listBrand.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listBrand, HttpStatus.OK);
    }
}
