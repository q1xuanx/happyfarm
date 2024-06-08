package Nhom02.Nhom02HappyFarm.api.brand;


import Nhom02.Nhom02HappyFarm.entities.Brand;
import Nhom02.Nhom02HappyFarm.entities.OriginFertilizer;
import Nhom02.Nhom02HappyFarm.service.BrandService;
import Nhom02.Nhom02HappyFarm.service.OriginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class BrandApi {
    private final BrandService brandService;

    @GetMapping("/getlistbrand")
    public ResponseEntity<List<Brand>> getListBrand(@RequestParam(required = false) String nameBrand){
        if (brandService.GetAllBrand(nameBrand).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(brandService.GetAllBrand(nameBrand), HttpStatus.OK);
    }

    @PostMapping("/addnew")
    public ResponseEntity<Brand> createNewBrand(@RequestBody Brand brand){
        try{
            brandService.AddOrEditBrand(brand);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception error) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/find/{name}")
    public ResponseEntity<List<Brand>> findByName(@PathVariable(name = "name") String name){
        List<Brand> list = brandService.findByName(name);
        if(list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PutMapping("/editbrand/{id}")
    public ResponseEntity<Brand> editBrand(@PathVariable(name = "id") String idBrand, @RequestBody Brand brand){
        Brand getBrand = brandService.GetBrand(idBrand);
        if (getBrand == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            brandService.AddOrEditBrand(brand);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    @DeleteMapping("/deletebrand/{id}")
    public ResponseEntity<Brand> deteleBrand(@PathVariable(name = "id") String idBrand){
        try {
            brandService.DeleteBrand(idBrand);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception error){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/notdeletebrand")
    public ResponseEntity<List<Brand>> brandIsNotDelete(){
        List<Brand> listBrand = brandService.GetAllOriginNotDelete();
        if (listBrand.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listBrand, HttpStatus.OK);
    }
}
