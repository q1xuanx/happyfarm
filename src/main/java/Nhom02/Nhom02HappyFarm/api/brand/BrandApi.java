package Nhom02.Nhom02HappyFarm.api.brand;


import Nhom02.Nhom02HappyFarm.entities.Brand;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Api(value = "Manage Brand Of Fertilizer")
public class BrandApi {
    private final BrandService brandService;
    private final ResponseHandler responseHandler;
    @ApiOperation(value = "Tra ve 1 list cac thuong hieu bao gom ca xoa va chua xoa")
    @ApiResponses(value ={
        @ApiResponse(code = 200, message = "Tra ve thanh cong"),
        @ApiResponse(code = 204, message = "List rong"), @ApiResponse(code = 400, message = "Lỗi khi lấy list")
    })
    @GetMapping("/getlistbrand")
    public ResponseEntity<Object> getListBrand(@RequestParam(required = false) String nameBrand){
        if (brandService.GetAllBrand(nameBrand).isEmpty()){
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List brand rong"));
        }
        return ResponseEntity.ok(responseHandler.successResponse("Lay list thanh cong", brandService.GetAllBrand(nameBrand)));
    }
    @ApiOperation(value = "Thêm mới 1 brand vào db")
    @ApiResponses(value ={
            @ApiResponse(code = 201, message = "Tạo mới thành công"),
            @ApiResponse(code = 401, message = "Có lỗi ve du lieu trong quá trình thêm, kiểm tra xem có dữ liệu nào null hay không")
    })
    @PostMapping("/addnew")
    public ResponseEntity<Object> createNewBrand(@RequestBody Brand brand){
        try{
            if (brand.getNameBrand().isEmpty()){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found name brand"));
            }
            brandService.AddOrEditBrand(brand);
            return ResponseEntity.ok(responseHandler.successResponse("Create succesfully", brand));
        }catch (Exception error) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(error.getMessage()));
        }
    }
    @ApiOperation(value = "Tìm 1 brand với tên trong DB (ten phải nhập đúng thì mới trả ra kết quả)")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Kết quả trả về thành công"),
            @ApiResponse(code = 401, message = "Có lỗi ve du lieu trong quá trình thêm, kiểm tra xem có dữ liệu nào null hay không")
    })
    @GetMapping("/find/{name}")
    public ResponseEntity<Object> findByName(@PathVariable(name = "name") String name){
        List<Brand> list = brandService.findByName(name);
        if(list.isEmpty()) {
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
        }
        return ResponseEntity.ok(responseHandler.successResponse("Succesfully get list", list));
    }
    @ApiOperation(value = "Lay 1 brand")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tao 1 brand thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay du lieu")
    })
    @GetMapping("/newBrand")
    public ResponseEntity<Object> createNew(){
        try{
            return ResponseEntity.ok(responseHandler.successResponse("Create new Brand", new Brand()));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }



    @ApiOperation(value = "Lay 1 brand bang id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lay thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay du lieu")
    })
    @GetMapping("/getbrand/{id}")
    public ResponseEntity<Object> getBrand(@PathVariable String id){
        try{
            Brand brand = brandService.GetBrand(id);
            if (brand == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found brand"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Get Brand successfully", brand));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Chỉnh sua brand trong DB")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Chinh sua thanh cong"),
            @ApiResponse(code = 404, message = "Không tìm thay Id cua brand cần chỉnh sửa")
    })
    @PutMapping("/editbrand/{id}")
    public ResponseEntity<Object> editBrand(@PathVariable(name = "id") String idBrand, @RequestBody Brand brand){
        Brand getBrand = brandService.GetBrand(idBrand);
        try {
            if (getBrand == null) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found brand"));
            } else {
                brandService.AddOrEditBrand(brand);
                return ResponseEntity.ok(responseHandler.successResponse("Edit brand success", brand));
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Xóa 1 brand với ID là tham số truyền vào")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Thành công"),
            @ApiResponse(code = 400, message = "Không tìm thay Id cua brand cần xoa")
    })
    @DeleteMapping("/deletebrand/{id}")
    public ResponseEntity<Object> deteleBrand(@PathVariable(name = "id") String idBrand){
        try {
            Brand brand = brandService.GetBrand(idBrand);
            if(brand == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found brand"));
            }
            brandService.DeleteBrand(idBrand);
            return ResponseEntity.ok(responseHandler.successResponse("Delete success", brand));
        }catch (Exception error){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(error.getMessage()));
        }
    }
    @ApiOperation(value = "Trả về các brand với IsDelete = false (Trả ve cac brand chưa xóa)")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Thành công"),
            @ApiResponse(code = 400, message = "Không tìm thay Id cua brand cần xoa"),
            @ApiResponse(code = 204, message = "List rỗng"),
    })
    @GetMapping("/notdeletebrand")
    public ResponseEntity<Object> brandIsNotDelete(){
        List<Brand> listBrand = brandService.GetAllOriginNotDelete();
        if (listBrand.isEmpty()){
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
        }
        return ResponseEntity.ok(responseHandler.successResponse("Success get list", listBrand));
    }
}
