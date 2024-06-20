package Nhom02.Nhom02HappyFarm.api.payment;


import Nhom02.Nhom02HappyFarm.entities.PaymentMethod;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.PaymentMethodService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/paymentmethod")
@Api(value = "Add new payment method to code")
@RequiredArgsConstructor
public class PaymentApi {

    private final PaymentMethodService paymentMethodService;
    private final ResponseHandler responseHandler;

    @GetMapping("/getlistpaymentnotdel")
    public ResponseEntity<Object> getListNotDelPaymentMethod() {
        try{
            return ResponseEntity.ok(responseHandler.successResponse("Get list success",paymentMethodService.getListPayment()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @GetMapping("/getlistpayment")
    public ResponseEntity<Object> getListPayment() {
        try{
            return ResponseEntity.ok(responseHandler.successResponse("Get list success",paymentMethodService.getListPayment()));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

}
