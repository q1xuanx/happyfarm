package Nhom02.Nhom02HappyFarm.api.checkoutapi;


import Nhom02.Nhom02HappyFarm.entities.Orders;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.CartItemService;
import Nhom02.Nhom02HappyFarm.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@Api(value = "Xu ly orders")
@RequiredArgsConstructor
@RequestMapping("/api/checkout")
@CrossOrigin(origins = "*")
public class CheckOutApi {
    private final OrdersService ordersService;
    private final CartItemService cartItemService;
    private final ResponseHandler responseHandler;
    @ApiOperation(value = "Lay thong tin user de mua hang")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @PostMapping("/getinfouser")
    public ResponseEntity<Object> getInfo(@RequestParam String idUser, @ModelAttribute Orders orders, HttpServletRequest req) throws ExecutionException {
        try {
            ordersService.addNewOrder(orders, cartItemService.listCart(idUser));
            if (orders.getPaymentMethod().getNameMethod().trim().equals("COD (Thanh toán khi nhận hàng)")){
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Đặt hàng thành công"));
            }else if (orders.getPaymentMethod().getNameMethod().equals("VnPAY")){
                return ResponseEntity.ok(responseHandler.successResponse("Thanh cong, chuyển hướng đến VNPAY", ordersService.vnpayHandle(req,orders)));
            }
            return ResponseEntity.badRequest().body(responseHandler.failResponse("Error"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Chuyen huong user den thanh toan vnpat hoac cam on neu ship cod")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @GetMapping("/info_payment")
    public ResponseEntity<Object> responsePayment(@RequestParam String vnp_Amount,@RequestParam String vnp_BankCode, @RequestParam String vnp_OrderInfo, @RequestParam String vnp_ResponseCode){
        if (vnp_ResponseCode.equals("00")){
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Thanh Toan Thanh Cong"));
        }
        return ResponseEntity.badRequest().body(responseHandler.failResponse("Co loi xay ra trong qua trinh thanh toan"));
    }
    @ApiOperation(value = "Edit status cua order")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @PutMapping("/editorder")
    public ResponseEntity<Object> editStatusOrder(@ModelAttribute Orders orders){
        try {
            ordersService.editOrder(orders);
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Edit thanh cong"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Lay lich su dat hang")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @GetMapping("/gethistoryorder")
    public ResponseEntity<Object> responseHistoryOrder(@RequestParam String idUser){
        try {
            return ResponseEntity.ok(responseHandler.successResponse("Get thanh cong", ordersService.getHistoryOrder(idUser)));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Lay chi tiet dat hang")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @GetMapping("/detailsorder")
    public ResponseEntity<Object> getDetailOrder(@RequestParam String idOrder){
        try {
            return ResponseEntity.ok(responseHandler.successResponse("Get thanh cong", ordersService.getDetailsOrder(idOrder)));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Lay tat ca order")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @GetMapping("/getall")
    public ResponseEntity<Object> getAll(){
        try {
            return ResponseEntity.ok(responseHandler.successResponse("Get thanh cong", ordersService.getAll()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
}
