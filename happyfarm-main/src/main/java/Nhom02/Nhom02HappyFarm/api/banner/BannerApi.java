package Nhom02.Nhom02HappyFarm.api.banner;


import Nhom02.Nhom02HappyFarm.entities.Banner;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/banner")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Api(value = "Add banner")
public class BannerApi {
    private final BannerService bannerService;
    private final ResponseHandler responseHandler;
    @ApiOperation(value = "Lay cac banner")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @GetMapping("/getall")
    public ResponseEntity<Object> getAll(){
        try {
            return ResponseEntity.ok(responseHandler.successResponse("Get thanh cong", bannerService.getAllBanner()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Add mot banner moi")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi trong qua trinh request")
    })
    @PostMapping("/addbanner")
    public ResponseEntity<Object> addBanner(@ModelAttribute Banner banner){
        try {
            if (!bannerService.checkExist(banner).equals("OK")){
                return ResponseEntity.badRequest().body(responseHandler.failResponse(bannerService.checkExist(banner)));
            }
            bannerService.addOrEditBanner(banner);
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Add banner thanh cong"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @PutMapping("/editbanner")
    public ResponseEntity<Object> editBanner(@ModelAttribute Banner banner){
        try {
            if (!bannerService.checkExist(banner).equals("OK")){
                return ResponseEntity.badRequest().body(responseHandler.failResponse(bannerService.checkExist(banner)));
            }
            bannerService.addOrEditBanner(banner);
            return ResponseEntity.ok(responseHandler.successResponse("Edit banner thanh cong", bannerService.getAllBanner()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @DeleteMapping("/deletebanner")
    public ResponseEntity<Object> deleteBanner(@RequestParam String id){
        try {
            Banner banner = bannerService.getBanner(id);
            if (banner == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            bannerService.deleteBanner(id);
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Xoa banner thanh cong"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
}
