package Nhom02.Nhom02HappyFarm.api.contacts;


import Nhom02.Nhom02HappyFarm.entities.Contacts;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.ContactsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Xu ly contacts")
@RequiredArgsConstructor
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*")
public class ContactsApi {
    private final ContactsService contactsService;
    private final ResponseHandler responseHandler;

    @ApiOperation(value = "Lay 1 list cac contact tu gan day nhat den xa nhat")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @GetMapping("/listcontact")
    public ResponseEntity<Object> listContact(){
        try{
            return ResponseEntity.ok(responseHandler.successResponse("Get list success", contactsService.getListContact()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Them moi 1 contact")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @PostMapping("/addcontact")
    public ResponseEntity<Object> addnewContact(@RequestBody Contacts contacts){
        try{
            if(!contactsService.checkExist(contacts).equals("OK")){
                return ResponseEntity.badRequest().body(responseHandler.failResponse(contactsService.checkExist(contacts)));
            }
            contactsService.addNew(contacts);
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Add thanh cong"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Chi tiet ve contact")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @GetMapping("/detailsContact")
    public ResponseEntity<Object> getDetails(@RequestParam String id){
        try{
            return ResponseEntity.ok(responseHandler.successResponse("Lay du lieu thanh cong", contactsService.getDetails(id)));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Xoa contact")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteContact(@RequestParam String id){
        try{
            contactsService.delete(id);
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Xoa thanh cong"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
}
