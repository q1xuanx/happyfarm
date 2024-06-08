package Nhom02.Nhom02HappyFarm.api.origin;

import Nhom02.Nhom02HappyFarm.entities.OriginFertilizer;
import Nhom02.Nhom02HappyFarm.entities.TypeFertilizer;
import Nhom02.Nhom02HappyFarm.service.OriginService;
import Nhom02.Nhom02HappyFarm.service.TypeFertilizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/origin")
@RequiredArgsConstructor
public class OriginApi {
    private final OriginService originService;

    @GetMapping("/getlistorigin")
    public ResponseEntity<List<OriginFertilizer>> getListOrigin(@RequestParam(required = false) String nameType){
        if (originService.GetAllOriginFertilizer(nameType).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(originService.GetAllOriginFertilizer(nameType), HttpStatus.OK);
    }

    @PostMapping("/addnew")
    public ResponseEntity<OriginFertilizer> createNewTypeFertilizer(@RequestBody OriginFertilizer originFertilizer){
        try{
            originService.AddOrEditOriginFertilizer(originFertilizer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception error) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
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
    @DeleteMapping("/deleteorigin/{id}")
    public ResponseEntity<OriginFertilizer> deleteTypeFertilizer(@PathVariable(name = "id") String idOrigin){
        try {
            originService.DeleteOriginFertilizer(idOrigin);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/find/{name}")
    public ResponseEntity<List<OriginFertilizer>> findByName(@PathVariable(name = "name") String name){
        List<OriginFertilizer> list = originService.findByName(name);
        if(list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/notdeleteorigin")
    public ResponseEntity<List<OriginFertilizer>> originIsNotDelete(){
        List<OriginFertilizer> listOrigin = originService.GetAllOriginNotDelete();
        if (listOrigin.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listOrigin, HttpStatus.OK);
    }
}
