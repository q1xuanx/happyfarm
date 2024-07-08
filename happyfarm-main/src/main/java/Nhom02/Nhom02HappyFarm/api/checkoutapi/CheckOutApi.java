package Nhom02.Nhom02HappyFarm.api.checkoutapi;


import Nhom02.Nhom02HappyFarm.entities.DetailsOrders;
import Nhom02.Nhom02HappyFarm.entities.Orders;
import Nhom02.Nhom02HappyFarm.entities.Users;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.CartItemService;
import Nhom02.Nhom02HappyFarm.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.hibernate.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<Object> getInfo(@RequestParam String idUser, @ModelAttribute Orders orders, HttpServletRequest req, HttpSession session) throws ExecutionException {
        try {
            if (!ordersService.checkQuantity(cartItemService.listCart(idUser))){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Quantity error"));
            }
            ordersService.addNewOrder(orders, cartItemService.listCart(idUser));
            if (orders.getPaymentMethod().getNameMethod().trim().equals("COD (Thanh toán khi nhận hàng)")){
                ordersService.sendEmail(orders.getIdUserOrder().getEmail(), ordersService.getDetailsOrder(orders.getIdOrders()));
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Đặt hàng thành công"));
            }else if (orders.getPaymentMethod().getNameMethod().equals("VnPAY")){
                return ResponseEntity.ok(responseHandler.successResponse("Thành công, chuyển hướng đến VNPAY", ordersService.vnpayHandle(req,orders)));
            }
            return ResponseEntity.badRequest().body(responseHandler.failResponse("Error"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Chuyen huong user den thanh toan vnpay")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @GetMapping("/info_payment")
    public ResponseEntity<Object> responsePayment(@RequestParam String vnp_Amount, @RequestParam String vnp_BankCode, @RequestParam String vnp_OrderInfo, @RequestParam String vnp_ResponseCode, HttpSession httpSession) throws MessagingException {
        if (vnp_ResponseCode.equals("00")){
            Orders getOrders = ordersService.getOrders(vnp_OrderInfo);
            ordersService.sendEmail(getOrders.getIdUserOrder().getEmail(), ordersService.getDetailsOrder(getOrders.getIdOrders()));
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Thanh Toán Thành Công"));
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
            if (orders == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found orders"));
            }
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

    @ApiOperation(value = "Get order status đã đặt theo thang/nam")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lay thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay du lieu")
    })
    @GetMapping("/monthly")
    public ResponseEntity<Object> getOrdersByMonthAndYear(@RequestParam int year, @RequestParam int month) {
        try {
            List<Orders> orders = ordersService.getOrdersByMonthAndYear(year, month);
            return ResponseEntity.ok(responseHandler.successResponse("Danh sách đơn hàng đã đặt trong tháng và năm", orders));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
}
