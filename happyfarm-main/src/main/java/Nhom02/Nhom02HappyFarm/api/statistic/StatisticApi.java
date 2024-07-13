package Nhom02.Nhom02HappyFarm.api.statistic;

import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/statistic")
@RequiredArgsConstructor
@Api(value = "Manage thong ke")
public class StatisticApi {

    @Autowired
    private OrdersService ordersService;
    private final ResponseHandler responseHandler;

    @ApiOperation(value = "Thong ke doanh thu tung thang nam")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lay thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay du lieu")
    })
    @GetMapping("/year")
    public ResponseEntity<Object> getMonthlyStatistics(@RequestParam int year) {
        try {
            List<Object[]> statistics = ordersService.getMonthlyStatistics(year);
            return ResponseEntity.ok(responseHandler.successResponse("Thống kê hàng tháng trong năm", statistics));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
}
