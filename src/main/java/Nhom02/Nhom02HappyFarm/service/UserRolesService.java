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

    public List<UserRoles> GetAllUserRoles(String name) throws Exception{
        try{
            if(name == null){
                return userRoles.findAll();
            }
            return userRoles.findAll().stream().filter(typename -> typename.getNameRoles().contains(name)).collect(Collectors.toList());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void AddOrEditUserRoles(UserRoles userRole) throws Exception{
        try {
            userRoles.save(userRole);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public UserRoles getRoleByName(String name) throws Exception{
        try {
            return  userRoles.findAll().stream().filter(s-> s.getNameRoles().equals(name)).findFirst().get();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public UserRoles GetUserRole(String id) throws Exception{
        try {
            Optional<UserRoles> userRole = userRoles.findById(id);
            return userRole.orElseThrow(() -> new NoSuchElementException("No userRole with " + id + " exits!"));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<UserRoles> GetUserRoleNotDelete() throws Exception{
        try {
            return userRoles.findByIsDelete(false);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<UserRoles> GetUserRoleByNameRole(String name) throws Exception{
        try {
            return userRoles.findByNameRoles(name);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void DeleteUserRole(String id) throws Exception{
        try {
            UserRoles userRole = userRoles.findById(id).orElseThrow(() -> new NoSuchElementException("No userRole with " + id + " exits!"));
            userRole.setDelete(true);
            userRoles.save(userRole);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
