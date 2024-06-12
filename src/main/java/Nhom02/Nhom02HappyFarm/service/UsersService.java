package Nhom02.Nhom02HappyFarm.service;

import Nhom02.Nhom02HappyFarm.entities.Users;
import Nhom02.Nhom02HappyFarm.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository users;

    public List<Users> GetAllUsers(String name) {
        if(name == null){
            return users.findAll();
        }
        return users.findAll().stream().filter(typename -> typename.getFullName().contains(name)).collect(Collectors.toList());
    }

    public void AddOrEditUser(Users user){
        users.save(user);
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
        return users.findBydOB(date);
    }

    public void BannedUser(String id){
        Users user = users.findById(id).orElseThrow(() -> new NoSuchElementException("No user with " + id + " exits!"));
        user.setBanned(true);
        users.save(user);
    }
}
