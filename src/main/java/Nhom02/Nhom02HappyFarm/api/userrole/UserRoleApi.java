package Nhom02.Nhom02HappyFarm.api.userrole;

import Nhom02.Nhom02HappyFarm.entities.UserRoles;
import Nhom02.Nhom02HappyFarm.entities.Users;
import Nhom02.Nhom02HappyFarm.service.UserRolesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "")
@RequestMapping("/api/userrole")
@RequiredArgsConstructor
@Api(value = "Manage UserRole")
public class UserRoleApi {
    private final UserRolesService userRolesService;

    @ApiOperation(value = "Tra ve 1 list userRole")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay gia tri tra ve list"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/getlistuserroles")
    public ResponseEntity<List<UserRoles>> getListUserRoles(@RequestParam(required = false) String nameUserRole) {
        if(userRolesService.GetAllUserRoles(nameUserRole).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userRolesService.GetAllUserRoles(nameUserRole), HttpStatus.OK);
    }

    @ApiOperation(value = "Them moi userrole")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Them userRole thanh cong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh them userRole, kiem tra xem co gia tri null hay khong ?")
    })
    @PostMapping("/addnew")
    public ResponseEntity<Users> createNewUserRole(@RequestBody UserRoles userRole) {
        try{
            userRolesService.AddOrEditUserRoles(userRole);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Edit 1 userRole")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Edit thanh cong"),
            @ApiResponse(code = 404, message = "Khong tim thay userRole, co the bi xoa hoac sai id")
    })
    @PutMapping("/edituserrole/{id}")
    public ResponseEntity<UserRoles> editUserRole(@PathVariable(name = "id") String id, @RequestBody UserRoles userRole) {
        UserRoles getUserRole = userRolesService.GetUserRole(id);
        if(getUserRole == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            userRolesService.AddOrEditUserRoles(userRole);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Tra ve list cac userRole chua bi delete")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay, tra ve list userRole"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/notdeleteuserrole")
    public ResponseEntity<List<UserRoles>> notDeleteUser() {
        List<UserRoles> userRoles = userRolesService.GetUserRoleNotDelete();
        if(userRoles.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }

    @ApiOperation(value = "Tim kiem ten userRole do admin nhap")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay gia tri tra ve"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/findbyname/{name}")
    public ResponseEntity<List<UserRoles>> findUserRoleByName(@PathVariable(name = "name") String name) {
        List<UserRoles> userRoles = userRolesService.GetUserRoleByNameRole(name);
        if(userRoles.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }

    @ApiOperation(value = "delete 1 userRole")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete thanh cong"),
            @ApiResponse(code = 404, message = "Khong tim thay userRole can tim, co the bi xoa hoac sai id")
    })
    @DeleteMapping("/deleteuserrole/{id}")
    public ResponseEntity<List<Users>> bannedUserById(@PathVariable(name = "id") String id){
        try{
            userRolesService.DeleteUserRole(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
