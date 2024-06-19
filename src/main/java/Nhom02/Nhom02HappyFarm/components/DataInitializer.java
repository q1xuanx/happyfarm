package Nhom02.Nhom02HappyFarm.components;


import Nhom02.Nhom02HappyFarm.entities.UserRoles;
import Nhom02.Nhom02HappyFarm.entities.Users;
import Nhom02.Nhom02HappyFarm.service.UserRolesService;
import Nhom02.Nhom02HappyFarm.service.UsersService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DataInitializer {
    private final UsersService usersService;
    private final UserRolesService userRolesService;

    @PostConstruct
    public void init(){
        List<String> getListRoles = listRoles();
        for(String roles : getListRoles){
            UserRoles userRoles = new UserRoles();
            userRoles.setNameRoles(roles);
            userRolesService.AddOrEditUserRoles(userRoles);
        }
        Users users = new Users();
        users.setUsername("admin");
        users.setPassword("admin");
        users.setDOB(new Date("17/09/2003"));
        users.setEmail("nhoang2929@gmail.com");
        users.setFullName("Hoang Nhan");
        users.setRoles(userRolesService.getRoleByName("ROLE_ADMIN"));
        usersService.initUser(users);
    }
    public List<String> listRoles(){
        List<String> listRoles = new ArrayList<>();
        listRoles.add("ROLE_ADMIN");
        listRoles.add("ROLE_USER");
        listRoles.add("ROLE_AGENCY");
        listRoles.add("ROLE_FARMER");
        listRoles.add("ROLE_HTX");
        listRoles.add("ROLE_COOPERATIVE");
        listRoles.add("ROLE_StaffWeb");
        listRoles.add("ROLE_StaffSale");
        return listRoles;
    }
}
