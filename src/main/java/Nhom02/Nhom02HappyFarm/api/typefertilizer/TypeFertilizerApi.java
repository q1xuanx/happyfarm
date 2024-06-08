package Nhom02.Nhom02HappyFarm.api.typefertilizer;


import Nhom02.Nhom02HappyFarm.entities.TypeFertilizer;
import Nhom02.Nhom02HappyFarm.service.TypeFertilizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/typefertilizer")
@RequiredArgsConstructor
public class TypeFertilizerApi {

    private final TypeFertilizerService typeFertilizerService;

    @GetMapping("/getlisttype")
    public ResponseEntity<List<TypeFertilizer>> getListTypeFer(@RequestParam(required = false) String nameType){
        if (typeFertilizerService.GetAllTypeFertilizer(nameType).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(typeFertilizerService.GetAllTypeFertilizer(nameType), HttpStatus.OK);
    }

    @PostMapping("/addnew")
    public ResponseEntity<TypeFertilizer> createNewTypeFertilizer(@RequestBody TypeFertilizer typeFertilizer){
        try{
            typeFertilizerService.AddOrEditTypeFertilizer(typeFertilizer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception error) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
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
    @GetMapping("/notdeltype")
    public ResponseEntity<List<TypeFertilizer>> NotDelType(){
        List<TypeFertilizer> typeFer = typeFertilizerService.GetTypeFertilizerNotDelete();
        if (typeFer.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(typeFer, HttpStatus.OK);
    }
    @GetMapping("/find/{name}")
    public ResponseEntity<List<TypeFertilizer>> findByName(@PathVariable(name = "name") String name){
        List<TypeFertilizer> list = typeFertilizerService.GetTypeByName(name);
        if(list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
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
