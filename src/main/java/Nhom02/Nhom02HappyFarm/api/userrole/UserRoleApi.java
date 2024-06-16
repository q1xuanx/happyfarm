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
@CrossOrigin(origins = "http://localhost:8080")
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
            usersService.AddOrEditUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Tra ve list cac user chua bi banned")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay, tra ve list user"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/notbanneduser")
    public ResponseEntity<List<Users>> notBannedUser() {
        List<Users> users = usersService.GetUserNotBanned();
        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiOperation(value = "Tim kiem ten user do admin nhap")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay gia tri tra ve"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/findbyname/{name}")
    public ResponseEntity<List<Users>> findUserByName(@PathVariable(name = "name") String name) {
        List<Users> users = usersService.GetUserByName(name);
        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiOperation(value = "Tim kiem ngay sinh user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay gia tri tra ve"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/findbydob/{dob}")
    public ResponseEntity<List<Users>> findUserByDOB(@PathVariable(name = "dob") Date date) {
        List<Users> users = usersService.GetUserByDob(date);
        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiOperation(value = "Banned 1 user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Banned thanh cong"),
            @ApiResponse(code = 404, message = "Khong tim thay user can tim, co the bi xoa hoac sai id")
    })
    @DeleteMapping("/banneduser/{id}")
    public ResponseEntity<List<Users>> bannedUserById(@PathVariable(name = "id") String id){
        try{
            usersService.BannedUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
