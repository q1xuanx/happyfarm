package Nhom02.Nhom02HappyFarm.api.origin;

import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.entities.OriginFertilizer;
import Nhom02.Nhom02HappyFarm.entities.TypeFertilizer;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.OriginService;
import Nhom02.Nhom02HappyFarm.service.TypeFertilizerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/origin")
@RequiredArgsConstructor
@Api(value = "Manage Xuat Xu")
public class OriginApi {
    private final OriginService originService;

    private final ResponseHandler responseHandler;

    @ApiOperation(value = "Tao moi 1 origin")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lay thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay du lieu")
    })
    @GetMapping("/neworigin")
    public ResponseEntity<Object> createNew() {
        try {
            return ResponseEntity.ok(responseHandler.successResponse("Created", new OriginFertilizer()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Lay 1 xuat xu cua origin bang id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lay thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay du lieu")
    })
    @GetMapping("/getorigin/{id}")
    public ResponseEntity<Object> getOrigin(@PathVariable String id) {
        try {
            OriginFertilizer origin = originService.GetOriginFertilizer(id);
            if (origin == null) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Get success", origin));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Tra ve 1 list cac xuat xu bao gom ca xoa va chua xoa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tra ve thanh cong"),
            @ApiResponse(code = 204, message = "List rong"), @ApiResponse(code = 400, message = "Lỗi khi lấy list")
    })
    @GetMapping("/getlistorigin")
    public ResponseEntity<Object> getListOrigin(@RequestParam(required = false) String nameType) {
        if (originService.GetAllOriginFertilizer(nameType).isEmpty()) {
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Empty list"));
        }
        return ResponseEntity.ok(responseHandler.successResponse("Get list", originService.GetAllOriginFertilizer(nameType)));
    }

    @ApiOperation(value = "Thêm mới 1 xuat xu vào db")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Tạo mới thành công"),
            @ApiResponse(code = 401, message = "Có lỗi ve du lieu trong quá trình thêm, kiểm tra xem có dữ liệu nào null hay không")
    })
    @PostMapping("/addnew")
    public ResponseEntity<Object> createNewTypeFertilizer(@Valid @RequestBody OriginFertilizer originFertilizer) {
        try {
            if (originFertilizer.getNameOrigin().isEmpty()) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Name null"));
            } else if (originService.existName(originFertilizer.getNameOrigin())) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Name exist"));
            }
            originService.AddOrEditOriginFertilizer(originFertilizer);
            return ResponseEntity.ok(responseHandler.successResponse("Created", originFertilizer));
        } catch (Exception error) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Chinh sua 1 xuat xu vào db")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chinh sua thanh cong"),
            @ApiResponse(code = 404, message = "Khong the chinh sửa do ID không tồn tại hoặc đã bị xóa")
    })
    @PutMapping("/editorigin/{id}")
    public ResponseEntity<Object> editOrigin(@PathVariable(name = "id") String idType, @Valid @RequestBody OriginFertilizer originFertilizer) {
        OriginFertilizer getOrigin = originService.GetOriginFertilizer(idType);
        try {
            if (getOrigin == null) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            } else if (originFertilizer.getNameOrigin().isEmpty()) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Name null"));
            } else if (originService.existName(originFertilizer.getNameOrigin())) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Name exist"));
            }
            originService.AddOrEditOriginFertilizer(originFertilizer);
            return ResponseEntity.ok(responseHandler.successResponse("Add success", originFertilizer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Xóa 1 xuat xu trong db")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Da xoa thanh cong"),
            @ApiResponse(code = 401, message = "Khong the xóa do ID không tồn tại hoặc đã bị xóa")
    })
    @DeleteMapping("/deleteorigin/{id}")
    public ResponseEntity<Object> deleteTypeFertilizer(@PathVariable(name = "id") String idOrigin) {
        OriginFertilizer getOrigin = originService.GetOriginFertilizer(idOrigin);
        try {
            if (getOrigin == null) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            originService.DeleteOriginFertilizer(idOrigin);
            return ResponseEntity.ok(responseHandler.successResponse("Origin is deleted", idOrigin));
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(error.getMessage()));
        }
    }

    @ApiOperation(value = "Tìm kiem trong DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay gia tri va tre ve list"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/find/{name}")
    public ResponseEntity<Object> findByName(@PathVariable(name = "name") String name) {
        List<OriginFertilizer> list = originService.findByName(name);
        if (list.isEmpty()) {
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
        }
        return ResponseEntity.ok(responseHandler.successResponse("Get list thanh cong", list));
    }

    @ApiOperation(value = "Tra ve 1 list cac xuat xu voi IsDelete = false (Tuc chua xoa)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay gia tri va tre ve list"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/notdeleteorigin")
    public ResponseEntity<Object> originIsNotDelete() {
        List<OriginFertilizer> listOrigin = originService.GetAllOriginNotDelete();
        if (listOrigin.isEmpty()) {
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
        }
        return ResponseEntity.ok(responseHandler.successResponse("Get list thanh cong", listOrigin));
    }

    @ApiOperation(value = "Tra ve 1 list cac xuat xu voi IsDelete = true (Tuc trong thung rac)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay gia tri va tre ve list"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/listdel")
    public ResponseEntity<Object> listDel() {
        List<OriginFertilizer> listOrigin = originService.GetAllOriginDelete();
        if (listOrigin.isEmpty()) {
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
        }
        return ResponseEntity.ok(responseHandler.successResponse("Get list thanh cong", listOrigin));
    }

    @ApiOperation(value = "Xóa ra khỏi Database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thấy và da xoa"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @DeleteMapping("/confirmDelete")
    public ResponseEntity<Object> confirmDelete(@RequestParam String id) {
        try {
            if (originService.confirmDelete(id) == 1) {
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Đã xóa "));
            }
            return ResponseEntity.badRequest().body(responseHandler.failResponse("Có lỗi, chưa tạo origin hoặc không có id cần xóa"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
}
