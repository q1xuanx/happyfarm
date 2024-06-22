package Nhom02.Nhom02HappyFarm.api.voucher;

import Nhom02.Nhom02HappyFarm.entities.PaymentMethod;
import Nhom02.Nhom02HappyFarm.entities.VoucherDiscount;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.PaymentMethodService;
import Nhom02.Nhom02HappyFarm.service.VoucherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voucher")
@Api(value = "Add new voucher")
@CrossOrigin(origins = "")
@RequiredArgsConstructor
public class VoucherApi {
    private final VoucherService voucherService;
    private final ResponseHandler responseHandler;

    @ApiOperation(value = "Lay list cac loai voucher chua delete")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @GetMapping("/getlistvouchernotdelete")
    public ResponseEntity<Object> getListVoucherNotDelete() {
        try{
            return ResponseEntity.ok(responseHandler.successResponse("Get list success",voucherService.getListNotDelteVoucher()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Lay tat ca cac loai voucher bao gom ca delete")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @GetMapping("/getallvoucher")
    public ResponseEntity<Object> getListPayment() {
        try{
            return ResponseEntity.ok(responseHandler.successResponse("Get list success",voucherService.getListVoucher()));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Khoi tao mot voucher moi")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @GetMapping("/addnew")
    public ResponseEntity<Object> addNewVoucher() {
        return ResponseEntity.ok(responseHandler.successResponse("Create new", new VoucherDiscount()));
    }
    @ApiOperation(value = "Them moi Voucher")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @PostMapping("/addnew")
    public ResponseEntity<Object> addNewVoucher(@RequestBody VoucherDiscount voucher) {
        try{
            if (voucher.getCodeVoucher().isEmpty()){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Code method is empty"));
            }
            voucherService.AddOrEditPaymentMethod(voucher);
            return ResponseEntity.ok().body(responseHandler.successResponseButNotHaveContent("Tao moi thanh cong"));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Tim kiem voucher theo id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @GetMapping("/getvoucher/{id}")
    public ResponseEntity<Object> getPaymentById(@PathVariable String id) {
        try{
            VoucherDiscount voucher = voucherService.getVoucher(id);
            if (voucher == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            return ResponseEntity.ok().body(responseHandler.successResponse("Lay du lieu thanh cong", voucher));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Chinh sua payment method")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @PutMapping("/editvoucher")
    public ResponseEntity<Object> editVoucher(@RequestBody VoucherDiscount voucher) {
        try{
            if (voucher.getCodeVoucher().isEmpty()){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Code method is empty"));
            }
            voucherService.AddOrEditPaymentMethod(voucher);
            return ResponseEntity.ok().body(responseHandler.successResponse("Edit thanh cong", voucher));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Xoa voucher Method")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deletePayment(@PathVariable String id) {
        try {
            VoucherDiscount voucher = voucherService.getVoucher(id);
            if (voucher == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            voucherService.DeleteVoucher(id);
            return ResponseEntity.ok().body(responseHandler.successResponseButNotHaveContent("Xoa thanh cong"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
}
