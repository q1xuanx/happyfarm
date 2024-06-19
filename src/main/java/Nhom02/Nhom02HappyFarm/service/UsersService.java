package Nhom02.Nhom02HappyFarm.service;

import Nhom02.Nhom02HappyFarm.entities.UserRoles;
import Nhom02.Nhom02HappyFarm.entities.Users;
import Nhom02.Nhom02HappyFarm.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository users;
    private final UserRolesService rolesService;
    public List<Users> GetAllUsers(String name) {
        if(name == null){
            return users.findAll();
        }
        return users.findAll().stream().filter(typename -> typename.getFullName().contains(name)).collect(Collectors.toList());
    }

    public void initUser(Users users){
        this.users.save(users);
    }



    public void AddOrEditUser(Users user){
        boolean find = users.findAll().stream().filter(s -> s.getIdUser().equals(user.getIdUser())).findFirst().isPresent();
        if(!find){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            UserRoles roles = rolesService.getRoleByName("ROLE_USER");
            user.setRoles(roles);
            users.save(user);
        }else {
            //Change password || edit information users
            Users getUser = users.findById(user.getIdUser()).get();
            String password = getUser.getPassword();
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            boolean isMatch = passwordEncoder.matches(password, user.getPassword());
            if (!isMatch){
                getUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            users.save(getUser);
        }
    }

    public Users GetUser(String id){
        Optional<Users> user = users.findById(id);
        return user.orElseThrow(() -> new NoSuchElementException("No user with " + id + " exits!"));
    }

    public List<Users> GetUserNotBanned(){
        return users.findByIsBanned(false);
    }

    public List<Users> GetUserByName(String name){
        return users.findByFullName(name);
    }

    public List<Users> GetUserByDob(Date date){
        return users.findAll().stream().filter(s -> s.getDOB().compareTo(date) == 1).collect(Collectors.toList());
    }

    public void BannedUser(String id){
        Users user = users.findById(id).orElseThrow(() -> new NoSuchElementException("No user with " + id + " exits!"));
        user.setBanned(true);
        users.save(user);
    }
}
