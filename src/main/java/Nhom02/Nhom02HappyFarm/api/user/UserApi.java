package Nhom02.Nhom02HappyFarm.api.user;


import Nhom02.Nhom02HappyFarm.entities.Users;
import Nhom02.Nhom02HappyFarm.service.UsersService;
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
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Api(value = "Manage User")
public class UserApi {
    private final UsersService usersService;

    @ApiOperation(value = "Tra ve 1 list user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tim thay gia tri tra ve list"),
            @ApiResponse(code = 204, message = "List rong"),
            @ApiResponse(code = 404, message = "Co loi xay ra trong qua trinh tim kiem")
    })
    @GetMapping("/getlistusers")
    public ResponseEntity<List<Users>> getListUsers(@RequestParam(required = false) String nameUser) {
        if(usersService.GetAllUsers(nameUser).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usersService.GetAllUsers(nameUser), HttpStatus.OK);
    }

    @ApiOperation(value = "Them moi user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Them user thanh cong"),
            @ApiResponse(code = 404, message = "Co loi xay ra torng qua trinh them user, kiem tra xem co gia tri null hay khong ?")
    })
    @PostMapping("/addnew")
    public ResponseEntity<Object> createNewUser(@ModelAttribute Users user) {
        try{
            usersService.AddOrEditUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Edit 1 user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Edit thanh cong"),
        @ApiResponse(code = 404, message = "Khong tim thay user, co the bi xoa hoac sai id")
    })
    @PutMapping("/edituser/{id}")
    public ResponseEntity<Users> editUser(@PathVariable(name = "id") String id, @RequestBody Users user) {
        Users getUser = usersService.GetUser(id);
        if(getUser == null){
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
    @GetMapping("/findbydob/")
    public ResponseEntity<List<Users>> findUserByDOB(@RequestParam(name = "dob") Date date) {
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