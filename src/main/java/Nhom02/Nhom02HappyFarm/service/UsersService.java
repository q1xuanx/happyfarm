package Nhom02.Nhom02HappyFarm.service;

import Nhom02.Nhom02HappyFarm.entities.UserRoles;
import Nhom02.Nhom02HappyFarm.entities.Users;
import Nhom02.Nhom02HappyFarm.repository.UserRolesRepository;
import Nhom02.Nhom02HappyFarm.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository users;
    private final UserRolesService rolesService;
    private final UserRolesRepository userRolesRepository;

    public List<Users> GetAllUsers(String name) throws Exception{
        try {
            if(name == null){
                return users.findAll();
            }
            return users.findAll().stream().filter(typename -> typename.getFullName().contains(name)).collect(Collectors.toList());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public void initUser(Users users) throws Exception{
        try {
            this.users.save(users);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public void changePassword(Users user) throws Exception {
        try{
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

            if(user.getIdUser() != null){
                Users existingUser = users.findById(user.getIdUser()).orElse(null);
                if (existingUser != null) {
                    existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
                    users.save(existingUser);
                } else {
                    throw new Exception("User not found");
                }
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void AddNewUser(Users user){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        users.save(user);
    }

    //Service nay de dang ky
    public void AddOrEditUser(Users user) throws Exception{
        try {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            if(user.getIdUser() == null){
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                UserRoles roles = rolesService.getRoleByName("ROLE_USER");
                user.setRoles(roles);
                users.save(user);
            }else {
                Users existingUser = users.findById(user.getIdUser()).get();
                existingUser.setUsername(user.getUsername());
                existingUser.setFullName(user.getFullName());
                existingUser.setEmail(user.getEmail());
                existingUser.setDob(user.getDob());
                existingUser.setBanned(user.isBanned());
                UserRoles newRole = rolesService.getRoleByName(user.getRoles().getNameRoles());
                existingUser.setRoles(newRole);
                users.save(existingUser);
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public String checkExist(Users user){
        if (user.getUsername().isEmpty()){
            return "Username not found";
        }else if (user.getDob().isEmpty()){
            return "Date of birth not found";
        }else if (user.getEmail().isEmpty()){
            return "Email not found";
        }else if (user.getPassword().isEmpty()){
            return "Password not found";
        }
        return "OK";
    }

    public boolean checkEmail(String email){
        return users.findAll().stream().anyMatch(s -> s.getEmail().equals(email));
    }

    public boolean checkEmailForEdit(String email, String idUser){
        return users.findAll().stream().anyMatch(s -> s.getEmail().equals(email) && !s.getIdUser().equals(idUser));
    }

    public boolean checkExistUserName(String username){
        return users.findAll().stream().anyMatch(s -> s.getUsername().equals(username));
    }

    public boolean checkExistUserNameForEdit(String username, String idUser){
        return users.findAll().stream().anyMatch(s -> s.getUsername().equals(username) && !s.getIdUser().equals(idUser));
    }

    public Users GetUser(String id) throws Exception{
        try {
            Optional<Users> user = users.findById(id);
            return user.orElseThrow(() -> new NoSuchElementException("No user with " + id + " exits!"));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<Users> GetUserNotBanned() throws Exception{
        try {
            return users.findByIsBanned(false);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<Users> GetUserByName(String name) throws Exception{
        try {
            return users.findByFullName(name);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<Users> GetUserByDob(String date) throws Exception{
        try {
            String trimmedDate = date.trim(); // Loại bỏ khoảng trắng

            return users.findAll()
                    .stream()
                    .filter(user -> user.getDob().trim().equals(trimmedDate)) // Lọc người dùng có dob bằng với date sau khi loại bỏ khoảng trắng
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void BannedUser(String id) throws Exception{
        try {
            Users user = users.findById(id).orElseThrow(() -> new NoSuchElementException("No user with " + id + " exits!"));
            user.setBanned(true);
            users.save(user);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public Optional<Users> login (String username, String password){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return users.findAll().stream().filter(s -> s.getUsername().equals(username) && passwordEncoder.matches(password, s.getPassword()) && !s.isBanned()).findFirst();
    }
}
