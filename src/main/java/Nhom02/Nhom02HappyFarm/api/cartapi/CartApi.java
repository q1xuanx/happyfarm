package Nhom02.Nhom02HappyFarm.api.cartapi;


import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.CartItemService;
import Nhom02.Nhom02HappyFarm.service.FertilizerService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
@Api(value = "Add items to buy for users")
public class CartApi {
    private final FertilizerService fertilizerService;
    private final CartItemService cartItemService; 
    private final ResponseHandler responseHandler;

    @GetMapping("/getlistitem")
    public ResponseEntity<Object> getListItemOfUser(@RequestParam String idUser){
        try{
            return ResponseEntity.ok(responseHandler.successResponse("Get list cart of user", cartItemService.listCart(idUser)));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @GetMapping("/getalllistitem")
    public ResponseEntity<Object> getListItemOfUser(){
        try{
            return ResponseEntity.ok(responseHandler.successResponse("Get list cart of user", cartItemService.listCart("")));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }


    @PostMapping("/additems")
    public ResponseEntity<Object> addItems(@RequestParam String idItems, @RequestParam int quantity, @RequestParam String idUser){
        try{
            int isOk = cartItemService.addNewItem(idItems, quantity, idUser);
            if (isOk == 0){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Số lượng yêu cầu vượt quá trong kho"));
            }else if (isOk == 2){
                Fertilizer fertilizer = fertilizerService.GetFertilizer(idItems);
                return ResponseEntity.ok(responseHandler.successResponse("Add thanh cong vao gio hang", fertilizer));
            }else {
                Fertilizer fertilizer = fertilizerService.GetFertilizer(idItems);
                return ResponseEntity.ok(responseHandler.successResponse("Update so luong thanh cong", fertilizer));
            }
        }catch(Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @PutMapping("/edititems")
    public ResponseEntity<Object> edit(@RequestParam String idItems, @RequestParam int quantity, @RequestParam String idUser){
        try{
            int isOk = cartItemService.EditItem(idItems, quantity, idUser);
            if (isOk == 1){
                Fertilizer fertilizer = fertilizerService.GetFertilizer(idItems);
                return ResponseEntity.ok(responseHandler.successResponse("Add thanh cong vao gio hang", fertilizer));
            }else if (isOk == 0){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Vuot qua so luong trong trong kho"));
            }else{
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Khong tim thay san pham"));
            }
        }catch(Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @DeleteMapping("/removeitems")
    public ResponseEntity<Object> removeItem(@RequestParam String idUser, @RequestParam String items){
        try{
            if (cartItemService.removeItems(items,idUser)){
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Deleted"));
            }else {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
        }catch(Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
}
