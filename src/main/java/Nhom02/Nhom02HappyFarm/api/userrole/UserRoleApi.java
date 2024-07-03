package Nhom02.Nhom02HappyFarm.api.userrole;

import Nhom02.Nhom02HappyFarm.entities.UserRoles;
import Nhom02.Nhom02HappyFarm.entities.Users;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.UserRolesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/userrole")
@RequiredArgsConstructor
@Api(value = "Manage UserRole")
public class UserRoleApi {
    private final UserRolesService userRolesService;
    private final ResponseHandler responseHandler;

    @ApiOperation(value = "Tra ve 1 list userRole")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay gia tri tra ve list"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/getListUserRoles")
    public ResponseEntity<Object> getListUserRoles(@RequestParam(required = false) String nameUserRole) {
        try {
            List<UserRoles> userRoles = userRolesService.GetAllUserRoles(nameUserRole);
            if(userRoles.isEmpty()){
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Get thanh cong", userRoles));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Them moi UserRole")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Them userRole thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh them userRole, kiem tra xem co gia tri null hay khong ?")
    })
    @PostMapping("/addNew")
    public ResponseEntity<Object> createNewUserRole(@RequestBody UserRoles userRole) {
        try{
            userRolesService.AddOrEditUserRoles(userRole);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseHandler.successResponseButNotHaveContent("Tao moi thanh cong"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Edit 1 userRole")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Edit thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh edit userRole"),
            @ApiResponse(code = 404, message = "Khong tim thay userRole, co the bi xoa hoac sai id")
    })
    @PutMapping("/editUserRole/{id}")
    public ResponseEntity<Object> editUserRole(@PathVariable(name = "id") String id, @RequestBody UserRoles userRole) {
        try {
            UserRoles getUserRole = userRolesService.GetUserRole(id);
            if(getUserRole == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            } else {
                userRolesService.AddOrEditUserRoles(userRole);
                return ResponseEntity.ok(responseHandler.successResponse("Edit thanh cong", userRole));
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Tra ve list cac userRole chua bi delete")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay, tra ve list userRole"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/notDeleteUserRoles")
    public ResponseEntity<Object> notDeleteUser() {
        try {
            List<UserRoles> userRoles = userRolesService.GetUserRoleNotDelete();
            if(userRoles.isEmpty()){
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Get thanh cong", userRoles));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Tim kim ten userRole do admin nhap")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay gia tri tra ve"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Khong the tim kiem kiem tra lai name")
    })
    @GetMapping("/findByNameRole/{name}")
    public ResponseEntity<Object> findUserRoleByName(@PathVariable(name = "name") String name) {
        try {
            List<UserRoles> userRoles = userRolesService.GetUserRoleByNameRole(name);
            if(userRoles.isEmpty()){
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Get thanh cong", userRoles));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "delete 1 userRole")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete thanh cong"),
            @ApiResponse(code = 404, message = "Khong tim thay userRole can tim, co the bi xoa hoac sai id")
    })
    @DeleteMapping("/deleteUserRole/{id}")
    public ResponseEntity<Object> bannedUserById(@PathVariable(name = "id") String id){
        try{
            userRolesService.DeleteUserRole(id);
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Delete thanh cong"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
}
