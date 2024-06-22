package Nhom02.Nhom02HappyFarm.api.ratings;


import Nhom02.Nhom02HappyFarm.entities.Ratings;
import Nhom02.Nhom02HappyFarm.service.RatingsService;
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
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
@Api(value = "Api xu ly danh gia phan bon")
@CrossOrigin(origins = "")
public class RatingsApi {
    private final RatingsService ratingsService;

    @ApiOperation(value = "Tao moi 1 ratings")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lay thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay du lieu")
    })
    @GetMapping("/newratings")
    public ResponseEntity<Ratings> createNew(){
        try{
            return new ResponseEntity<>(new Ratings(), HttpStatus.OK);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/getList")
    @ApiOperation(value = "Lay danh rating cua cac phan bon danh cho admin quan ly comment")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lay list thanh cong")
    })
    public ResponseEntity<List<Ratings>> getList(){
        return new ResponseEntity<>(ratingsService.listRatings(), HttpStatus.OK);
    }

    @GetMapping("/getlist/{idFertizlier}")
    @ApiOperation(value = "Lay danh sach comment cua cac loai phan bon theo id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lay list thanh cong!"), @ApiResponse(code = 400, message = "Loi trong qua trinh lay danh sach")
    })
    public ResponseEntity<List<Ratings>> getListById(@PathVariable String idFertizlier){
        try{
            ratingsService.getRatingsById(idFertizlier);
            return new ResponseEntity<>(ratingsService.listRatings(), HttpStatus.OK);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/getrate/{id}")
    @ApiOperation(value = "Lay rating theo id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lay thanh cong!"), @ApiResponse(code = 400, message = "Loi trong qua trinh lay ")
    })
    public ResponseEntity<Ratings> getRating(@PathVariable String id){
        try{
            Ratings rate = ratingsService.getRatingsById(id);
            return new ResponseEntity<>(rate, HttpStatus.OK);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/addnew")
    @ApiOperation(value = "Them moi 1 ratings")
    @ApiResponses({
            @ApiResponse(code = 200, message = "them thanh cong"), @ApiResponse(code = 400, message = "Loi trong qua trinh thuc hien")
    })
    public ResponseEntity<Ratings> addNewRatings(@RequestBody Ratings ratings){
        try{
            ratingsService.addNewOrEdit(ratings);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    @ApiOperation(value = "Chinh sua 1 ratings")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Chinh sua thanh cong"), @ApiResponse(code = 400, message = "Loi trong qua trinh thuc hien")
    })
    public ResponseEntity<Ratings> editRating(@PathVariable("id") String id, @RequestBody Ratings ratings){
        try{
            Ratings rate = ratingsService.getRatingsById(id);
            if (rate != null){
                ratingsService.addNewOrEdit(ratings);
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @ApiOperation(value = "Xoa 1 ratings")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Xoa thanh cong"), @ApiResponse(code = 400, message = "Loi trong qua trinh thuc hien")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Ratings> deleteRating(@PathVariable("id") String id){
        try{
            Ratings rate = ratingsService.getRatingsById(id);
            if (rate != null){
                ratingsService.deleteRatingsById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
