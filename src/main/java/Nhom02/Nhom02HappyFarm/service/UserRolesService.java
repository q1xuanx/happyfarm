package Nhom02.Nhom02HappyFarm.service;

import Nhom02.Nhom02HappyFarm.entities.UserRoles;
import Nhom02.Nhom02HappyFarm.repository.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRolesService {
    private final UserRolesRepository userRoles;

    public List<UserRoles> GetAllUserRoles(String name) {
        if(name == null){
            return userRoles.findAll();
        }
        return userRoles.findAll().stream().filter(typename -> typename.getNameRoles().contains(name)).collect(Collectors.toList());
    }

    public void AddOrEditUserRoles(UserRoles userRole){
        userRoles.save(userRole);
    }

    public UserRoles getRoleByName(String name){
        return  userRoles.findAll().stream().filter(s-> s.getNameRoles().equals(name)).findFirst().get();
    }

    public UserRoles GetUserRole(String id){
        Optional<UserRoles> userRole = userRoles.findById(id);
        return userRole.orElseThrow(() -> new NoSuchElementException("No userRole with " + id + " exits!"));
    }


    public List<UserRoles> GetUserRoleNotDelete(){
        return userRoles.findByIsDelete(false);
    }

    public List<UserRoles> GetUserRoleByNameRole(String name){
        return userRoles.findByNameRoles(name);
    }

    public void DeleteUserRole(String id){
        UserRoles userRole = userRoles.findById(id).orElseThrow(() -> new NoSuchElementException("No userRole with " + id + " exits!"));
        userRole.setDelete(true);
        userRoles.save(userRole);
    }
}
