package Nhom02.Nhom02HappyFarm.api.user;

import Nhom02.Nhom02HappyFarm.dto.request.ApiReponse;
import Nhom02.Nhom02HappyFarm.dto.request.UserCreateRequest;
import Nhom02.Nhom02HappyFarm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.plugins.Docket;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class userApi {

    private final UserService userService;

    @PostMapping
    public ApiReponse<UserCreateRequest> createUser(@RequestBody UserCreateRequest userCreateRequest){
        ApiReponse<UserCreateRequest> apiReponse = new ApiReponse<>();
        apiReponse.builder()
                .message("thanh cong")
                .result(userService.getUsers(userCreateRequest))
                .build();
        return apiReponse;
    }
}