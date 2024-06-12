package Nhom02.Nhom02HappyFarm.api.user;

import Nhom02.Nhom02HappyFarm.dto.request.ApiReponse;
import Nhom02.Nhom02HappyFarm.dto.request.UserCreateRequest;
import Nhom02.Nhom02HappyFarm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
public class userApi {

    UserService userService;

    @GetMapping()
    ApiReponse<UserCreateRequest> createUser(UserCreateRequest userCreateRequest){
        ApiReponse apiReponse = new ApiReponse<>();
        apiReponse.builder()
                .message("thanh cong")
                .result(userService.getUsers(userCreateRequest))
                .build();
    }

}
