package Nhom02.Nhom02HappyFarm.api.origin;

import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.entities.OriginFertilizer;
import Nhom02.Nhom02HappyFarm.entities.TypeFertilizer;
import Nhom02.Nhom02HappyFarm.service.OriginService;
import Nhom02.Nhom02HappyFarm.service.TypeFertilizerService;
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
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/origin")
@RequiredArgsConstructor
@Api(value = "Manage Xuat Xu")
public class OriginApi {
    private final OriginService originService;
    @ApiOperation(value = "Tao moi 1 origin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lay thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay du lieu")
    })
    @GetMapping("/neworigin")
    public ResponseEntity<OriginFertilizer> createNew(){
        try{
            return new ResponseEntity<>(new OriginFertilizer(), HttpStatus.OK);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @ApiOperation(value = "Lay 1 xuat xu cua origin bang id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lay thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay du lieu")
    })
    @GetMapping("/getorigin/{id}")
    public ResponseEntity<OriginFertilizer> getOrigin(@PathVariable String id){
        try{
            OriginFertilizer origin = originService.GetOriginFertilizer(id);
            return new ResponseEntity<>(origin, HttpStatus.OK);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @ApiOperation(value = "Tra ve 1 list cac xuat xu bao gom ca xoa va chua xoa")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Tra ve thanh cong"),
            @ApiResponse(code = 204, message = "List rong"), @ApiResponse(code = 400, message = "Lỗi khi lấy list")
    })
    @GetMapping("/getlistorigin")
    public ResponseEntity<List<OriginFertilizer>> getListOrigin(@RequestParam(required = false) String nameType){
        if (originService.GetAllOriginFertilizer(nameType).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(originService.GetAllOriginFertilizer(nameType), HttpStatus.OK);
    }
    @ApiOperation(value = "Thêm mới 1 xuat xu vào db")
    @ApiResponses(value ={
            @ApiResponse(code = 201, message = "Tạo mới thành công"),
            @ApiResponse(code = 401, message = "Có lỗi ve du lieu trong quá trình thêm, kiểm tra xem có dữ liệu nào null hay không")
    })
    @PostMapping("/addnew")
    public ResponseEntity<OriginFertilizer> createNewTypeFertilizer(@RequestBody OriginFertilizer originFertilizer){
        try{
            originService.AddOrEditOriginFertilizer(originFertilizer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception error) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @ApiOperation(value = "Chinh sua 1 xuat xu vào db")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Chinh sua thanh cong"),
            @ApiResponse(code = 404, message = "Khong the chinh sửa do ID không tồn tại hoặc đã bị xóa")
    })
    @PutMapping("/editorigin/{id}")
    public ResponseEntity<OriginFertilizer> editOrigin(@PathVariable(name = "id") String idType, @RequestBody OriginFertilizer originFertilizer){
        OriginFertilizer getOrigin = originService.GetOriginFertilizer(idType);
        if (getOrigin == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            originService.AddOrEditOriginFertilizer(originFertilizer);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    @ApiOperation(value = "Xóa 1 xuat xu trong db")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Da xoa thanh cong"),
            @ApiResponse(code = 401, message = "Khong the xóa do ID không tồn tại hoặc đã bị xóa")
    })
    @DeleteMapping("/deleteorigin/{id}")
    public ResponseEntity<OriginFertilizer> deleteTypeFertilizer(@PathVariable(name = "id") String idOrigin){
        try {
            originService.DeleteOriginFertilizer(idOrigin);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @ApiOperation(value = "Tìm kiem trong DB")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Tim thay gia tri va tre ve list"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/find/{name}")
    public ResponseEntity<List<OriginFertilizer>> findByName(@PathVariable(name = "name") String name){
        List<OriginFertilizer> list = originService.findByName(name);
        if(list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @ApiOperation(value = "Tra ve 1 list cac xuat xu voi IsDelete = false (Tuc chua xoa)")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Tim thay gia tri va tre ve list"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/notdeleteorigin")
    public ResponseEntity<List<OriginFertilizer>> originIsNotDelete(){
        List<OriginFertilizer> listOrigin = originService.GetAllOriginNotDelete();
        if (listOrigin.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listOrigin, HttpStatus.OK);
    }
}
