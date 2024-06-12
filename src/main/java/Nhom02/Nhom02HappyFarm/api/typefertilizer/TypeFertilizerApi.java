package Nhom02.Nhom02HappyFarm.api.typefertilizer;


import Nhom02.Nhom02HappyFarm.entities.TypeFertilizer;
import Nhom02.Nhom02HappyFarm.service.TypeFertilizerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/typefertilizer")
@RequiredArgsConstructor
@Api(value = "Quan ly cac loai phan bon")
public class TypeFertilizerApi {

    private final TypeFertilizerService typeFertilizerService;
    @ApiOperation(value = "Tra ve 1 list cac xuat")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Tim thay gia tri va tre ve list"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/getlisttype")
    public ResponseEntity<List<TypeFertilizer>> getListTypeFer(@RequestParam(required = false) String nameType){
        if (typeFertilizerService.GetAllTypeFertilizer(nameType).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(typeFertilizerService.GetAllTypeFertilizer(nameType), HttpStatus.OK);
    }
    @ApiOperation(value = "Them moi 1 loai phan bón")
    @ApiResponses(value ={
            @ApiResponse(code = 201, message = "Tạo thành công"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tạo, vui lòng kiem tra xem có giá trị null hay không ?")
    })
    @PostMapping("/addnew")
    public ResponseEntity<TypeFertilizer> createNewTypeFertilizer(@RequestBody TypeFertilizer typeFertilizer){
        try{
            typeFertilizerService.AddOrEditTypeFertilizer(typeFertilizer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception error) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @ApiOperation(value = "Edit 1 loai phan bón")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Edit thành công"),
            @ApiResponse(code = 404, message = "Khong tim thay loai phan bon can tim, co the bi xoa hoac sai ID")
    })
    @PutMapping("/editfertilizer/{id}")
    public ResponseEntity<TypeFertilizer> editFertilizer(@PathVariable(name = "id") String idType, @RequestBody TypeFertilizer typeFertilizer){
        TypeFertilizer getType = typeFertilizerService.GetTypeFertilizer(idType);
        if (getType == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            typeFertilizerService.AddOrEditTypeFertilizer(typeFertilizer);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    @ApiOperation(value = "Tra ve 1 list cac xuat voi cac loai phan bon chua bi xoa (Isdelete = false)")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Tim thay gia tri va tre ve list"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/notdeltype")
    public ResponseEntity<List<TypeFertilizer>> NotDelType(){
        List<TypeFertilizer> typeFer = typeFertilizerService.GetTypeFertilizerNotDelete();
        if (typeFer.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(typeFer, HttpStatus.OK);
    }
    @ApiOperation(value = "Tìm loại phân bón với tên do nguoi dung nhap")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Tim thay gia tri va tre ve"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/find/{name}")
    public ResponseEntity<List<TypeFertilizer>> findByName(@PathVariable(name = "name") String name){
        List<TypeFertilizer> list = typeFertilizerService.GetTypeByName(name);
        if(list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }



    @ApiOperation(value = "Xoa 1 loai phan bón")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Xoa thành công"),
            @ApiResponse(code = 404, message = "Khong tim thay loai phan bon can tim, co the bi xoa hoac sai ID")
    })
    @DeleteMapping("/deletefertilizer/{id}")
    public ResponseEntity<TypeFertilizer> deleteTypeFertilizer(@PathVariable(name = "id") String idType){
        try {
            typeFertilizerService.DeleteTypeFertilizer(idType);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
