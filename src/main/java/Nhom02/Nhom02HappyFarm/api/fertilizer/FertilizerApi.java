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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
            if (fertilizerService.checkName(fertilizer.getNameFertilizer())){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Tên đã bị trùng"));
            }
            if (!fertilizerService.checkExistForAdd(fertilizer).equals("OK")){
                return ResponseEntity.badRequest().body(responseHandler.failResponse(fertilizerService.checkExistForAdd(fertilizer)));
            }
            fertilizerService.addNew(fertilizer);
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Tạo mới thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Edit Image")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Thành công"),
            @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình trả về")})
    @GetMapping("/editImage")
    public ResponseEntity<Object> editImage(@RequestParam String id, @RequestParam String url, @ModelAttribute MultipartFile imageFile){
        try{
            fertilizerService.editImageFertilizer(id,url,imageFile);
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Edit thanh cong"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Delete Image")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Thành công"),
            @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình trả về")})
    @DeleteMapping("/deleteimage")
    public ResponseEntity<Object> deleteImage(@RequestParam String id, @RequestParam String url){
        try{
            fertilizerService.deleteImage(id,url);
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Delete thanh cong"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @ApiOperation(value = "Tìm thông tin phan bon theo URL")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Thành công"),
            @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình trả về")})
    @GetMapping("/fertilizerget/{url}")
    public ResponseEntity<Object> getFertilizerByURL(@PathVariable String url){
        try {
            Fertilizer fer = fertilizerService.getFertlizerByURL(url);
            if (fer == null) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found !"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Get thanh cong", fer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Trả về 1 list các phân bón bao gom đã IsDelete = 1 và 0")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Thành công"),
            @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình trả về")})
    @GetMapping("/listallfer")
    public ResponseEntity<Object> getAllFertilizer(@RequestParam int numberOfPage, @RequestParam int sizeOfPage) {
        try {
            Page<Fertilizer> listFertilizer = fertilizerService.listFertilizer(numberOfPage, sizeOfPage);
            if (listFertilizer.isEmpty()) {
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Not found"));
            }
            return ResponseEntity.ok(responseHandler.successAndPage("Lay list thanh cong", listFertilizer.getContent(), listFertilizer.getTotalPages()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Trả về 1 list các phân bón bao gom đã IsDelete = false")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình trả về")})
    @GetMapping("/listfer")
    public ResponseEntity<Object> getAllFerNotDell(@RequestParam int numberOfPage, @RequestParam int sizeOfPage) {
        try {
            Page<Fertilizer> listFertilizer = fertilizerService.disPlayFertilizer(numberOfPage, sizeOfPage);
            return ResponseEntity.ok(responseHandler.successAndPage("Get list thanh cong", listFertilizer.getContent(), listFertilizer.getTotalPages()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Trả về 1 list các phân bón bao gom đã IsDelete = true")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình trả về")})
    @GetMapping("/listferdel")
    public ResponseEntity<Object> getAllFertilizerDel(@RequestParam int numberOfPage, int sizeOfPage) {
        try {
            Page<Fertilizer> listFertilizer = fertilizerService.FertilizerDel(numberOfPage, sizeOfPage);
            return ResponseEntity.ok(responseHandler.successAndPage("Get list thanh cong", listFertilizer.getContent(), listFertilizer.getTotalPages()));
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
    public ResponseEntity<Object> EditFertilizer(@PathVariable String id, @Valid @ModelAttribute Fertilizer fertilizer) {
        try {
            Fertilizer find = fertilizerService.GetFertilizer(id);
            if (find == null) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            if (!fertilizerService.checkExistForEdit(fertilizer).equals("OK")){
                return ResponseEntity.badRequest().body(responseHandler.failResponse(fertilizerService.checkExistForEdit(fertilizer)));
            }
            fertilizerService.EditFertilizer(fertilizer);
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Edit thanh cong"));
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
    public ResponseEntity<Object> filer(@RequestParam int numberOfPage, @RequestParam int sizeOfPage, @RequestParam(required = false) String nameBrand, @RequestParam(required = false) String origin, @RequestParam(required = false) String typFer) {
        try {
            return ResponseEntity.ok(responseHandler.successAndPage("Lay list thanh cong", fertilizerService.filter(numberOfPage, sizeOfPage, nameBrand,origin,typFer).getContent(),fertilizerService.filter(numberOfPage, sizeOfPage, nameBrand,origin,typFer).getTotalPages()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }

    @ApiOperation(value = "Loc phan bón qua ten ten loai phan bon")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @GetMapping("/filterbytype")
    public ResponseEntity<Object> filterByType(@RequestParam int pageNumber, @RequestParam int sizeOfPage, @RequestParam(required = false) String typFer) {
        try {
            Page<Fertilizer> paged = fertilizerService.filterByType(pageNumber, sizeOfPage, typFer);
            return ResponseEntity.ok(responseHandler.successAndPage("Find Success",paged.getContent(), paged.getTotalPages()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }
    @ApiOperation(value = "Loc phan bón qua ten xuat xu")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @GetMapping("/filterbyorigin")
    public ResponseEntity<Object> filterByOrigin(@RequestParam int pageNumber, @RequestParam int sizeOfPage, @RequestParam(required = false) String origin) {
        try {
            Page<Fertilizer> paged = fertilizerService.filterByOrigin(pageNumber, sizeOfPage, origin);
            return ResponseEntity.ok(responseHandler.successAndPage("Find Success",paged.getContent(), paged.getTotalPages()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }
    @ApiOperation(value = "Loc phan bón qua brand")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @GetMapping("/filterbybrand")
    public ResponseEntity<Object> filterByBrand(@RequestParam int pageNumber, @RequestParam int sizeOfPage, @RequestParam(required = false) String brand) {
        try {
            Page<Fertilizer> paged = fertilizerService.filterByBrand(pageNumber, sizeOfPage, brand);
            return ResponseEntity.ok(responseHandler.successAndPage("Find Success",paged.getContent(), paged.getTotalPages()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }
    @ApiOperation(value = "Tim phan bón qua ten")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @GetMapping("/findbyname")
    public ResponseEntity<Object> findName(@RequestParam int pageNumber, @RequestParam int sizeOfPage, @RequestParam String name) {
        try {
            Page<Fertilizer> page = fertilizerService.findFertilizerByName(pageNumber,sizeOfPage,name);
            return ResponseEntity.ok(responseHandler.successAndPage("Find Success",page.getContent(),page.getTotalPages()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }
    @ApiOperation(value = "Loc phan bon theo gia")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @GetMapping("/fillbyprice")
    public ResponseEntity<Object> fillByPrice(@RequestParam int numberOfPage, @RequestParam int sizeOfPage, @RequestParam int price) {
        try {
            Page<Fertilizer> list = fertilizerService.filByPrice(numberOfPage,sizeOfPage, (float) price);
            return ResponseEntity.ok(responseHandler.successAndPage("Find Success", list.getContent(), list.getTotalPages()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }

    @ApiOperation(value = "Delete phan bon ra khoi database")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @DeleteMapping("/confirmdel")
    public ResponseEntity<Object> deleteOutDatabase(@RequestParam String id) {
        try {
            if(fertilizerService.deleteFertilizerOut(id) == 1){
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Đã xóa thành công"));
            }
            return ResponseEntity.badRequest().body(responseHandler.failResponse("Có lỗi trong qua trinh xóa"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(ex.getMessage()));
        }
    }

    @ApiOperation(value = "Lay ra list phan bon ban nhieu nhat")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @GetMapping("/mostbuy")
    public ResponseEntity<Object> mostBuyFertilizer(){
        try{
            Map<String, Integer> countMostBuy = detailsOrderService.getAll().stream().collect(Collectors.groupingBy(DetailsOrders::getNameFertilizer, Collectors.summingInt(DetailsOrders::getQuantity)));
            List<String> comPareQuantity = countMostBuy.entrySet().stream().sorted((q1, q2) -> q2.getValue().compareTo(q1.getValue())).limit(3).map(Map.Entry::getKey).toList();
            List<Fertilizer> list = new ArrayList<>();
            for (String s : comPareQuantity){
                list.add(fertilizerService.findExacfertilizer(s));
            }
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
