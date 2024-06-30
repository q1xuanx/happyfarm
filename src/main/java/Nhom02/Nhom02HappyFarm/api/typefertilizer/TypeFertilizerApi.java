package Nhom02.Nhom02HappyFarm.api.typefertilizer;

import Nhom02.Nhom02HappyFarm.entities.TypeFertilizer;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
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
@CrossOrigin(origins = "*")
@RequestMapping("/api/typefertilizer")
@RequiredArgsConstructor
@Api(value = "Quan ly cac loai phan bon")
public class TypeFertilizerApi {
    private final TypeFertilizerService typeFertilizerService;
    private final ResponseHandler responseHandler;

    @ApiOperation(value = "Tra ve 1 list cac loai phan bon")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Tim thay gia tri va tre ve list"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/getListType")
    public ResponseEntity<Object> getListTypeFer(@RequestParam(required = false) String nameType){
        try {
            List<TypeFertilizer> typeFertilizer = typeFertilizerService.GetAllTypeFertilizer(nameType);
            if(typeFertilizer.isEmpty()){
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Get list thanh cong", typeFertilizer));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Them moi 1 loai phan bón")
    @ApiResponses(value ={
            @ApiResponse(code = 201, message = "Tạo thành công"),
            @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình thêm mới")
    })
    @PostMapping("/addnew")
    public ResponseEntity<Object> createNewTypeFertilizer(@RequestBody TypeFertilizer typeFertilizer){
        try{
            typeFertilizerService.AddOrEditTypeFertilizer(typeFertilizer);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseHandler.successResponseButNotHaveContent("Tạo mới thành công"));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Edit 1 loai phan bón")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Edit thành công"),
            @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình edit"),
            @ApiResponse(code = 404, message = "Khong tim thay loai phan bon can tim, co the bi xoa hoac sai ID")
    })
    @PutMapping("/editTypeFertilizer/{id}")
    public ResponseEntity<Object> editFertilizer(@PathVariable String id, @RequestBody TypeFertilizer typeFertilizer){
        try {
            TypeFertilizer getType = typeFertilizerService.GetTypeFertilizer(id);
            if (getType == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }else {
                typeFertilizerService.AddOrEditTypeFertilizer(typeFertilizer);
                return ResponseEntity.ok(responseHandler.successResponse("Edit thanh cong", typeFertilizer));
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }

    }

    @ApiOperation(value = "Tra ve 1 list cac xuat voi cac loai phan bon chua bi xoa (Isdelete = false)")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Tim thay gia tri va tre ve list"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/notDelType")
    public ResponseEntity<Object> NotDelType(){
        try {
            List<TypeFertilizer> typeFer = typeFertilizerService.GetTypeFertilizerNotDelete();
            if(typeFer.isEmpty()){
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Get thanh cong", typeFer));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Tra ve 1 list cac xuat voi cac loai phan bon chua bi xoa (Isdelete = true)")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Tim thay gia tri va tre ve list"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/deletelist")
    public ResponseEntity<Object> delType(){
        try {
            List<TypeFertilizer> typeFer = typeFertilizerService.GetTypeFertilizerDelete();
            if(typeFer.isEmpty()){
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Get thanh cong", typeFer));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Tìm loại phân bón với tên do nguoi dung nhap")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Tim thay gia tri va tre ve"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/find/{name}")
    public ResponseEntity<Object> findByName(@PathVariable String name){
        try {
            List<TypeFertilizer> list = typeFertilizerService.GetTypeByName(name);
            if(list.isEmpty()){
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Get thanh cong", list));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Xoa 1 loai phan bón")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Xoa thành công"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh xoa"),
            @ApiResponse(code = 404, message = "Khong tim thay loai phan bon can tim, co the bi xoa hoac sai ID")
    })
    @DeleteMapping("/deleteTypeFertilizer/{id}")
    public ResponseEntity<Object> deleteTypeFertilizer(@PathVariable(name = "id") String idType){
        try {
            TypeFertilizer typeFertilizer = typeFertilizerService.GetTypeFertilizer(idType);
            if (typeFertilizer == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            typeFertilizerService.DeleteTypeFertilizer(idType);
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Xoa thanh cong"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Xoa 1 loai phan bón khoi db")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Xoa thành công"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh xoa"),
    })
    @DeleteMapping("/confirmdelete")
    public ResponseEntity<Object> confirmDelete(@RequestParam String idType){
        try {
            if (typeFertilizerService.ConfirmDelete(idType) == 1){
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Da xoa thanh cong khoi DB"));
            }
            return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
}
