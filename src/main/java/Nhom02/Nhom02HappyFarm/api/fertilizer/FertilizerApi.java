package Nhom02.Nhom02HappyFarm.api.fertilizer;


import Nhom02.Nhom02HappyFarm.entities.Blog;
import Nhom02.Nhom02HappyFarm.entities.Brand;
import Nhom02.Nhom02HappyFarm.entities.DetailsOrders;
import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.DetailsOrderService;
import Nhom02.Nhom02HappyFarm.service.FertilizerService;
import io.swagger.annotations.*;
import io.swagger.models.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/fertilizer")
@Api(value = "Quản lý và lọc các loại phân bón")
@RequiredArgsConstructor
public class FertilizerApi {
    private final FertilizerService fertilizerService;
    private final ResponseHandler responseHandler;
    private final DetailsOrderService detailsOrderService;
    @ApiOperation(value = "Tao moi 1 fertilizer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lay thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay du lieu")
    })
    @GetMapping("/newFertilizer")
    public ResponseEntity<Object> createNew() {
        try {
            return ResponseEntity.ok(responseHandler.successResponse("Tao moi thanh cong", new Fertilizer()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Lay 1 phan bon bang id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lay thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay du lieu")
    })
    @GetMapping("/getfertilizer/{id}")
    public ResponseEntity<Object> getFertilizer(@PathVariable String id) {
        try {
            Fertilizer fertilizer = fertilizerService.GetFertilizer(id);
            if (fertilizer == null) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Get thanh cong", fertilizer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @PostMapping("/addnew")
    @ApiOperation(value = "Thêm mới 1 phan bón")
    @ApiResponses({@ApiResponse(code = 201, message = "Thêm mới thành công"),
            @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình thêm mới")})
    public ResponseEntity<Object> createNewFertilizer(@Valid @ModelAttribute Fertilizer fertilizer) throws IOException {
        try {
            fertilizerService.addNew(fertilizer);
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Tạo mới thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Trả về 1 list các phân bón bao gom đã IsDelete = 1 và 0")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Thành công"),
            @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình trả về")})
    @GetMapping("/listallfer")
    public ResponseEntity<Object> getAllFertilizer() {
        try {
            List<Fertilizer> listFertilizer = fertilizerService.listFertilizer();
            if (listFertilizer.isEmpty()) {
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Not found"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Lay list thanh cong", listFertilizer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Trả về 1 list các phân bón bao gom đã IsDelete = false")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình trả về")})
    @GetMapping("/listfer")
    public ResponseEntity<Object> getAllFerNotDell() {
        try {
            List<Fertilizer> listFertilizer = fertilizerService.FertilizerNotDelete();
            return ResponseEntity.ok(responseHandler.successResponse("Get list thanh cong", listFertilizer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Chinh sua phan bón")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thành công"),
            @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau"),
            @ApiResponse(code = 404, message = "Khong tim thay id yeu cau")})
    @PutMapping("/editfertilizer/{id}")
    public ResponseEntity<Object> EditFertilizer(@PathVariable String id, @Valid @RequestBody Fertilizer fertilizer) {
        try {
            Fertilizer find = fertilizerService.GetFertilizer(id);
            if (find == null) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            fertilizerService.EditFertilizer(fertilizer);
            return ResponseEntity.ok(responseHandler.successResponse("Edit thanh cong", fertilizer));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }

    @ApiOperation(value = "Xoa phan bón")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @DeleteMapping("/deletefer/{id}")
    public ResponseEntity<Object> deleteFertilizer(@PathVariable String id) {
        try {
            Fertilizer find = fertilizerService.GetFertilizer(id);
            if (find == null) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            fertilizerService.DeleteFertilizer(id);
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Xoa thanh cong"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }
    @ApiOperation(value = "Loc phan bón qua ten thuonng hieu, ten xuat xu, ten loai phan bon")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @GetMapping("/filer")
    public ResponseEntity<Object> filer(@RequestParam(required = false) String nameBrand, @RequestParam(required = false) String origin, @RequestParam(required = false) String typFer) {
        try {
            return ResponseEntity.ok(responseHandler.successResponse("Lay list thanh cong", fertilizerService.filter(nameBrand,origin,typFer)));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }

    @ApiOperation(value = "Loc phan bón qua ten ten loai phan bon")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @GetMapping("/filterbytype")
    public ResponseEntity<Object> filterByType(@RequestParam(required = false) String typFer) {
        try {
            return ResponseEntity.ok(responseHandler.successResponse("Find Success",fertilizerService.filterByType(typFer)));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }
    @ApiOperation(value = "Tim phan bón qua ten")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @GetMapping("/findbyname")
    public ResponseEntity<Object> findName(@RequestParam String name) {
        try {
            return ResponseEntity.ok(responseHandler.successResponse("Find Success",fertilizerService.findFertilizerByName(name)));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }
    @ApiOperation(value = "Loc phan bon theo gia")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @GetMapping("/fillbyprice")
    public ResponseEntity<Object> fillByPrice(@RequestParam int price) {
        try {
            List<Fertilizer> list = fertilizerService.FertilizerNotDelete().stream().filter(s -> s.getPrice() <= price).toList();
            return ResponseEntity.ok(responseHandler.successResponse("Find Success", list));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }
    @ApiOperation(value = "Lay ra list phan bon ban nhieu nhat")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @GetMapping("/mostbuy")
    public ResponseEntity<Object> mostBuyFertilizer(){
        try{
            Map<Fertilizer, Integer> countMostBuy = detailsOrderService.getAll().stream().collect(Collectors.groupingBy(DetailsOrders::getIdFertilizer, Collectors.summingInt(DetailsOrders::getQuantity)));
            List<Fertilizer> list = countMostBuy.entrySet().stream().sorted((q1, q2) -> q2.getValue().compareTo(q1.getValue())).limit(3).map(Map.Entry::getKey).toList();
            return ResponseEntity.ok(responseHandler.successResponse("Most Buy", list));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }
    @ApiOperation(value = "Lay ra list phan bon ban nhieu nhat")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @GetMapping("/recentadd")
    public ResponseEntity<Object> recentAdded(){
        try{
            List<Fertilizer> list = fertilizerService.FertilizerNotDelete().stream().toList().reversed().stream().limit(3).toList();
            return ResponseEntity.ok(responseHandler.successResponse("Recent added", list));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }
    @ApiOperation(value = "Lay ra list phan bon ban nhieu nhat")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @GetMapping("/cheaper")
    public ResponseEntity<Object> getCheapPrice(){
        try{
            List<Fertilizer> list = fertilizerService.FertilizerNotDelete().stream().toList().stream().sorted((e1, e2) -> Float.compare(e1.getPrice(), e2.getPrice())).limit(3).toList();
            return ResponseEntity.ok(responseHandler.successResponse("Cheap fertilizer", list));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }
    @ApiOperation(value = "Lay ra list phan bon ban nhieu nhat")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @GetMapping("/expensive")
    public ResponseEntity<Object> getExpensiveFer(){
        try{
            List<Fertilizer> list = fertilizerService.FertilizerNotDelete().stream().toList().stream().sorted((e1, e2) -> Float.compare(e1.getPrice(), e2.getPrice())).toList().reversed().stream().limit(3).toList();
            return ResponseEntity.ok(responseHandler.successResponse("Expensive fertilizer", list));
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }
}
