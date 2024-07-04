package Nhom02.Nhom02HappyFarm.api.user;


import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.entities.UserRoles;
import Nhom02.Nhom02HappyFarm.entities.Users;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Api(value = "Manage User")
public class UserApi {
    private final UsersService usersService;
    private final ResponseHandler responseHandler;

    @ApiOperation(value = "Tra ve 1 list user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay gia tri tra ve list"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/getListUsers")
    public ResponseEntity<Object> getListUsers(@RequestParam(required = false) String nameUser) {
        try {
            List<Users> usersList = usersService.GetAllUsers(nameUser);
            if (usersList.isEmpty()) {
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Lay list thanh cong", usersList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Dang nhap")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dang nhap thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh dang nhap")
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login( @RequestParam String username,  @RequestParam String password) {
        Optional<Users> getUser = usersService.login(username, password);
        if (getUser.isPresent()) {
            Users user = getUser.get();
            return ResponseEntity.ok(responseHandler.successResponse("Đăng nhập thành công", user));
        }
        return ResponseEntity.badRequest().body(responseHandler.failResponse("Tài khoản hoặc mật khẩu không tồn tại hoặc tài khoản đã bị khóa"));
    }

    @ApiOperation(value = "Them moi user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Them user thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh them user, kiem tra xem co gia tri null hay khong ?")
    })
    @PostMapping("/addNew")
    public ResponseEntity<Object> createNewUser(@ModelAttribute Users user, @RequestParam(required = false) String nameRoles) {
        try {
            if(!usersService.checkExist(user).equals("OK")){
                return ResponseEntity.badRequest().body(responseHandler.failResponse(usersService.checkExist(user)));
            }else if (usersService.checkExistUserName(user.getUsername())){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("User name exist"));
            }
            usersService.AddOrEditUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseHandler.successResponseButNotHaveContent("Tạo mới thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Edit 1 user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Edit thanh cong"),
            @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau"),
            @ApiResponse(code = 404, message = "Khong tim thay user, co the bi xoa hoac sai id")
    })
    @PutMapping("/editUser/{id}")
    public ResponseEntity<Object> editUser(@PathVariable(name = "id") String id, @ModelAttribute Users user, @RequestParam(required = false) String nameRoles) {
        try {
            Users getUser = usersService.GetUser(id);
            if (getUser == null) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            } else {
                if(!usersService.checkExist(user).equals("OK")){
                    return ResponseEntity.badRequest().body(responseHandler.failResponse(usersService.checkExist(user)));
                }
                usersService.AddOrEditUser(user);
                return ResponseEntity.ok(responseHandler.successResponse("Edit thanh cong", user));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Doi pass 1 user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "doi pass thanh cong"),
            @ApiResponse(code = 400, message = "Có lỗi xảy ra trong quá trình gui yeu cau"),
            @ApiResponse(code = 404, message = "Khong tim thay user, co the bi xoa hoac sai id")
    })
    @PutMapping("/changePassUser/{id}")
    public ResponseEntity<Object> changePassUser(@PathVariable(name = "id") String id, @ModelAttribute Users user) {
        try{
            Users getUser = usersService.GetUser(id);
            if(getUser == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            } else {
                usersService.changePassword(user);
                return ResponseEntity.ok(responseHandler.successResponse("Doi mat khau thanh cong", user));
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Tra ve list cac user chua bi banned")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay, tra ve list user"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/getListUsersNotBanned")
    public ResponseEntity<Object> notBannedUser() {
        try {
            List<Users> users = usersService.GetUserNotBanned();
            if (users.isEmpty()) {
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Get thanh cong", users));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Tim kiem ten user do admin nhap")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay gia tri tra ve"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/findByName/{name}")
    public ResponseEntity<Object> findUserByName(@PathVariable(name = "name") String name) {
        try {
            List<Users> users = usersService.GetUserByName(name);
            if (users.isEmpty()) {
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Get thanh cong", users));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Tim kiem user theo ngay sinh")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay gia tri tra ve"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/findByDob")
    public ResponseEntity<Object> findUserByDob(@RequestParam(name = "dob") String dob) {
        try {
            List<Users> users = usersService.GetUserByDob(dob);
            if (users.isEmpty()) {
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("List rong"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Get thanh cong", users));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Banned 1 user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Banned thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh banned"),
            @ApiResponse(code = 404, message = "Khong tim thay user can tim, co the bi xoa hoac sai id")
    })
    @DeleteMapping("/bannedUser/{id}")
    public ResponseEntity<Object> bannedUserById(@PathVariable(name = "id") String id) {
        try {
            usersService.BannedUser(id);
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Banned thanh cong"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
}