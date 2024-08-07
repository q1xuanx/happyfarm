package Nhom02.Nhom02HappyFarm.api.ratings;



import Nhom02.Nhom02HappyFarm.entities.Ratings;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.RatingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
@Api(value = "Api xu ly danh gia phan bon")
@CrossOrigin(origins = "*")
public class RatingsApi {
    private final RatingsService ratingsService;
    private final ResponseHandler responseHandler;

    @GetMapping("/newRate")
    @ApiOperation(value = "Tao moi 1 ratings")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lay thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh tao moi")
    })
    public ResponseEntity<Object> createNew(){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(responseHandler.successResponse("Tao moi thanh cong", new Ratings()));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @GetMapping("/getList")
    @ApiOperation(value = "Lay list rating cua cac phan bon cho admin quan ly comment")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lay list thanh cong"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình trả về")
    })
    public ResponseEntity<Object> getList(){
         try{
             List<Ratings> ratingsList = ratingsService.listRatings();
             if(ratingsList.isEmpty()){
                 return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
             }
             return ResponseEntity.ok(responseHandler.successResponse("Lay list thanh cong", ratingsList));
         }catch(Exception e){
             return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
         }
    }

    @GetMapping("/getAvg")
    @ApiOperation(value = "Lay diem danh gia trung binh cua phan bon")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tinh thanh cong"),
            @ApiResponse(code = 400, message = "Loi trong qua trinh tinh toan"),
    })
    public ResponseEntity<Object> avgRate(@RequestParam String idFer){
        try{
            return ResponseEntity.ok(responseHandler.successResponse("Tinh thanh cong", ratingsService.avgPointRatings(idFer)));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse("Loi trong qua trinh tinh toan"));
        }
    }

    @GetMapping("/countrate")
    @ApiOperation(value = "Lay diem danh gia trung binh cua phan bon")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Loi trong qua trinh tinh toan"),
    })
    public ResponseEntity<Object> countRate(@RequestParam String idFer){
        try{
            return ResponseEntity.ok(responseHandler.successResponse("Tinh thanh cong", ratingsService.totalRatings(idFer)));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse("Loi trong qua trinh tinh toan"));
        }
    }

    @GetMapping("/getListByIdFer/{idFer}")
    @ApiOperation(value = "Lay danh sach comment cua phan bon theo idFer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lay list thanh cong!"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 400, message = "Loi trong qua trinh lay danh sach"),
            @ApiResponse(code = 404, message = "Khong tim thay id yeu cau")
    })
    public ResponseEntity<Object> getListByIdFer(@PathVariable String idFer){
        try{
            List<Ratings> ratingsList = ratingsService.getListByIdFer(idFer);
            if (ratingsList.isEmpty()) {
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Lay list thanh cong", ratingsList));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @GetMapping("/getRate/{id}")
    @ApiOperation(value = "Lay rating theo id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lay thanh cong!"),
            @ApiResponse(code = 400, message = "Loi trong qua trinh lay "),
            @ApiResponse(code = 404, message = "Khong tim thay id yeu cau")
    })
    public ResponseEntity<Object> getRating(@PathVariable String id){
        try{
            Ratings rate = ratingsService.getRatingsById(id);
            if (rate == null) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Get thanh cong", rate));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @PostMapping("/addnew")
    @ApiOperation(value = "Them moi 1 ratings")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Them thanh cong"),
            @ApiResponse(code = 400, message = "Loi trong qua trinh thuc hien")
    })
    public ResponseEntity<Object> addNewRatings(@ModelAttribute Ratings ratings){
        try{
            if (!ratingsService.checkExist(ratings).equals("OK")){
                return ResponseEntity.badRequest().body(responseHandler.failResponse(ratingsService.checkExist(ratings)));
            }
            ratingsService.addNewOrEdit(ratings);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseHandler.successResponseButNotHaveContent("Tạo mới thành công"));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @PutMapping("/edit/{id}")
    @ApiOperation(value = "Chinh sua 1 rating")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Chinh sua thanh cong"),
            @ApiResponse(code = 400, message = "Loi trong qua trinh thuc hien"),
            @ApiResponse(code = 404, message = "Khong tim thay id yeu cau")
    })
    public ResponseEntity<Object> editRating(@PathVariable String id,@Valid @ModelAttribute Ratings ratings){
        try{
            Ratings rate = ratingsService.getRatingsById(id);
            if (rate == null) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            if (!ratingsService.checkExist(ratings).equals("OK")){
                return ResponseEntity.badRequest().body(responseHandler.failResponse(ratingsService.checkExist(ratings)));
            }
            ratingsService.addNewOrEdit(ratings);
            return ResponseEntity.ok(responseHandler.successResponse("Edit thanh cong", ratings));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Xoa 1 rating")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Xoa thanh cong"),
            @ApiResponse(code = 400, message = "Loi trong qua trinh thuc hien"),
            @ApiResponse(code = 404, message = "Khong tim thay id yeu cau")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteRating(@PathVariable String id){
        try{
            Ratings rate = ratingsService.getRatingsById(id);
            if (rate == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            ratingsService.deleteRatingsById(id);
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Xoa thanh cong"));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
}
