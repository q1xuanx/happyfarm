package Nhom02.Nhom02HappyFarm.api.fertilizer;


import Nhom02.Nhom02HappyFarm.entities.Brand;
import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.service.FertilizerService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/fertilizer")
@Api(value = "Quản lý và lọc các loại phân bón")
@RequiredArgsConstructor
public class FertilizerApi {
    private final FertilizerService fertilizerService;
    @ApiOperation(value = "Tao moi 1 fertilizer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lay thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay du lieu")
    })
    @GetMapping("/newFertilizer")
    public ResponseEntity<Fertilizer> createNew(){
        try{
            return new ResponseEntity<>(new Fertilizer(), HttpStatus.OK);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @ApiOperation(value = "Lay 1 phan bon bang id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lay thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay du lieu")
    })
    @GetMapping("/getfertilizer/{id}")
    public ResponseEntity<Fertilizer> getFertilizer(@PathVariable String id){
        try{
            Fertilizer fertilizer = fertilizerService.GetFertilizer(id);
            return new ResponseEntity<>(fertilizer, HttpStatus.OK);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @PostMapping("/addnew")
    @ApiOperation(value = "Thêm mới 1 phan bón")
    @ApiResponses({@ApiResponse(code = 201, message = "Thêm mới thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình thêm mới")})
    public ResponseEntity<Fertilizer> uploadImage(@ModelAttribute Fertilizer fertilizer) throws IOException {
        try {
            fertilizerService.addNew(fertilizer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @ApiOperation(value = "Trả về 1 list các phân bón bao gom đã IsDelete = 1 và 0")
    @ApiResponses({@ApiResponse(code = 201, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình trả về")})
    @GetMapping("/listallfer")
    public ResponseEntity<List<Fertilizer>> getAllFertilizer(){
        try {
            List<Fertilizer> listFertilizer = fertilizerService.listFertilizer();
            return new ResponseEntity<>(listFertilizer, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @ApiOperation(value = "Trả về 1 list các phân bón bao gom đã IsDelete = false")
    @ApiResponses({@ApiResponse(code = 201, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình trả về")})
    @GetMapping("/listfer")
    public ResponseEntity<List<Fertilizer>> getAllFerNotDell(){
        try{
            List<Fertilizer> listFertilizer = fertilizerService.FertilizerNotDelete();
            return new ResponseEntity<>(listFertilizer, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @ApiOperation(value = "Chinh sua phan bón")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau"),
            @ApiResponse(code = 404, message = "Khong tim thay id yeu cau")})
    @PutMapping("/editfertilizer/{id}")
    public ResponseEntity<Fertilizer> EditFertilizer(@PathVariable String id, @RequestBody Fertilizer fertilizer){
        try{
            Fertilizer find = fertilizerService.GetFertilizer(id);
            if(find == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else {
                fertilizerService.EditFertilizer(fertilizer);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @ApiOperation(value = "Xoa phan bón")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @DeleteMapping("/deletefer/{id}")
    public ResponseEntity<Fertilizer> deleteFertilizer(@PathVariable String id){
        try {
            fertilizerService.DeleteFertilizer(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @ApiOperation(value = "Loc phan bón qua ten thuonng hieu, ten xuat xu, ten loai phan bon")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @GetMapping("/filer")
    public ResponseEntity<List<Fertilizer>> filer(@RequestParam(required = false) String nameBrand, @RequestParam(required = false) String origin, @RequestParam(required = false) String typFer){
        try{
            return new ResponseEntity<>(fertilizerService.filter(nameBrand,origin,typFer), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Tim phan bón qua ten")
    @ApiResponses({@ApiResponse(code = 200, message = "Thành công"), @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau")})
    @GetMapping("/findbyname")
    public ResponseEntity<List<Fertilizer>> findName(@RequestParam String name){
        try{
            return new ResponseEntity<>(fertilizerService.findFertilizerByName(name), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
