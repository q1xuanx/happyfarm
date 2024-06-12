package Nhom02.Nhom02HappyFarm.service;

import Nhom02.Nhom02HappyFarm.dto.request.UserCreateRequest;
import Nhom02.Nhom02HappyFarm.dto.response.UserResponse;
import Nhom02.Nhom02HappyFarm.entities.Users;
import Nhom02.Nhom02HappyFarm.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;

@RequiredArgsConstructor
public class UserService {


    UsersRepository  usersRepository;

    public UserResponse getUsers(UserCreateRequest userCreateRequest){
        Users user = new Users();
        UserResponse userResponse = new UserResponse();
        userResponse.builder()
                .fullName(user.getFullName())
                .dOB(user.getDOB())
                .roles(user.getRoles())
                .password(user.getPassword())
                .isBanned(user.isBanned())
                .build();
        return UserResponse;
    }


}
