package Nhom02.Nhom02HappyFarm.api.fertilizer;


import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.service.FertilizerService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/fertilizer")
@Api(value = "Quan ly va loc phan bon")
@RequiredArgsConstructor
public class FertilizerApi {
    private final FertilizerService fertilizerService;
    @PostMapping("/addnew")
    public ResponseEntity<Fertilizer> uploadImage(@ModelAttribute Fertilizer fertilizer) throws IOException {
        try {
            fertilizerService.addNew(fertilizer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/list")
    public ResponseEntity<List<Fertilizer>> getAllFertilizer(){
        try {
            List<Fertilizer> listFertilizer = fertilizerService.listFertilizer();
            return new ResponseEntity<>(listFertilizer, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
