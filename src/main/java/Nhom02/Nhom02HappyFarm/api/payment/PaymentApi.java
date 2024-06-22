package Nhom02.Nhom02HappyFarm.api.payment;


import Nhom02.Nhom02HappyFarm.entities.PaymentMethod;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.PaymentMethodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paymentmethod")
@Api(value = "Add new payment method to code")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PaymentApi {

    private final PaymentMethodService paymentMethodService;
    private final ResponseHandler responseHandler;

    @ApiOperation(value = "Lay list cac loai thanh toan chua delete")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @GetMapping("/getlistpaymentnotdel")
    public ResponseEntity<Object> getListNotDelPaymentMethod() {
        try{
            return ResponseEntity.ok(responseHandler.successResponse("Get list success",paymentMethodService.getListNotDeletedPayment()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Lay tat ca cac loai thanh toan bao gom ca delete")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @GetMapping("/getlistpayment")
    public ResponseEntity<Object> getListPayment() {
        try{
            return ResponseEntity.ok(responseHandler.successResponse("Get list success",paymentMethodService.getListPayment()));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Khoi tao mot Payment Method moi")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @GetMapping("/addnew")
    public ResponseEntity<Object> addNewPaymentMethod() {
        return ResponseEntity.ok(responseHandler.successResponse("Create new", new PaymentMethod()));
    }
    @ApiOperation(value = "Them moi Payment Method")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @PostMapping("/addnew")
    public ResponseEntity<Object> addNewPaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        try{
            if (paymentMethod.getNameMethod().isEmpty()){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Name method is empty"));
            }
            paymentMethodService.AddOrEditPaymentMethod(paymentMethod);
            return ResponseEntity.ok().body(responseHandler.successResponseButNotHaveContent("Tao moi thanh cong"));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Tim kiem payment theo id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @GetMapping("/getpayment/{id}")
    public ResponseEntity<Object> getPaymentById(@PathVariable String id) {
        try{
            PaymentMethod pay = paymentMethodService.getPaymentMethod(id);
            if (pay == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            return ResponseEntity.ok().body(responseHandler.successResponse("Lay du lieu thanh cong", pay));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Chinh sua payment method")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @PutMapping("/editpayment")
    public ResponseEntity<Object> editPayment(@RequestBody PaymentMethod paymentMethod) {
        try{
            if (paymentMethod.getNameMethod().isEmpty()){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Name method is empty"));
            }
            paymentMethodService.AddOrEditPaymentMethod(paymentMethod);
            return ResponseEntity.ok().body(responseHandler.successResponse("Edit thanh cong", paymentMethod));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Xoa Payment Method")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePayment(@PathVariable String id) {
        try {
            PaymentMethod pay = paymentMethodService.getPaymentMethod(id);
            if (pay == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            paymentMethodService.DeletePaymentMethod(id);
            return ResponseEntity.ok().body(responseHandler.successResponseButNotHaveContent("Xoa thanh cong"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
}
