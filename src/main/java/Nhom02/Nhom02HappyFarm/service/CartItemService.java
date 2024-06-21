package Nhom02.Nhom02HappyFarm.service;


import Nhom02.Nhom02HappyFarm.entities.CartItems;
import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.entities.Users;
import Nhom02.Nhom02HappyFarm.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private static final Logger log = LoggerFactory.getLogger(CartItemService.class);
    private final CartItemRepository cartItemRepository;
    private final UsersService usersService;
    private final FertilizerService fertilizerService;
    public int addNewItem(String idItems, int quantity, String idUser) throws IOException {
        List<CartItems> itemUserHave = cartItemRepository.findAll().stream().filter(s-> s.getUsers().getIdUser().equals(idUser)).collect(Collectors.toList());
        for (CartItems ca : itemUserHave){
            log.info(ca.getIdFertilizer().getIdFertilizer());
        }
        Optional<CartItems> items = itemUserHave.stream().filter(s -> s.getIdFertilizer().getIdFertilizer().equals(idItems)).findFirst();
        Fertilizer fertilizer = fertilizerService.GetFertilizer(idItems);
        Users getUser = usersService.GetUser(idUser);
        if (items.isPresent()){
            CartItems getItem = items.get();
            if (getItem.getQuantity() + quantity > fertilizer.getNums()){
                return 0;
            }
            getItem.setQuantity(getItem.getQuantity() + quantity);
            cartItemRepository.save(getItem);
            return 1;
        }
        CartItems cart = new CartItems();
        if (quantity > fertilizer.getNums()){
            return 0;
        }
        cart.setQuantity(quantity);
        cart.setUsers(getUser);
        cart.setIdFertilizer(fertilizer);
        cartItemRepository.save(cart);
        return 2;
    }
    public boolean removeItems(String idItems,String idUser){
        List<CartItems> itemUserHave = cartItemRepository.findAll().stream().filter(s-> s.getUsers().getIdUser().equals(idUser)).collect(Collectors.toList());
        Optional<CartItems> cart = itemUserHave.stream().filter(s -> s.getIdFertilizer().getIdFertilizer().equals(idItems)).findFirst();
        if (cart.isPresent()){
            cartItemRepository.delete(cart.get());
            return true;
        }else {
            return false;
        }
    }
    public int EditItem(String idItems, int quantity, String idUser) throws IOException {
        List<CartItems> itemUserHave = cartItemRepository.findAll().stream().filter(s-> s.getUsers().getIdUser().equals(idUser)).collect(Collectors.toList());
        Optional<CartItems> items = itemUserHave.stream().filter(s -> s.getIdFertilizer().getIdFertilizer().equals(idItems)).findFirst();
        Fertilizer fertilizer = fertilizerService.GetFertilizer(idItems);
        if (items.isPresent()){
            CartItems getItem = items.get();
            if (quantity > fertilizer.getNums()){
                return 0;
            }
            getItem.setQuantity(quantity);
            cartItemRepository.save(getItem);
            return 1;
        }
        return -1;
    }
    public List<CartItems> listCart (String idUser){
        if(idUser.isEmpty()){
            return cartItemRepository.findAll();
        }
        return cartItemRepository.findAll().stream().filter(s -> s.getUsers().getIdUser().equals(idUser)).toList();
    }
    @Transactional
    public void deleteCart(CartItems cart){
        cartItemRepository.delete(cart);
    }
}
