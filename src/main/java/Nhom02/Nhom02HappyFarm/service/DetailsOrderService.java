package Nhom02.Nhom02HappyFarm.service;


import Nhom02.Nhom02HappyFarm.entities.CartItems;
import Nhom02.Nhom02HappyFarm.entities.DetailsOrders;
import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.entities.Orders;
import Nhom02.Nhom02HappyFarm.repository.DetailsOrdersRepository;
import Nhom02.Nhom02HappyFarm.repository.FertilizerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class DetailsOrderService {
    private final DetailsOrdersRepository detailsOrdersRepository;
    private final FertilizerRepository fertilizerRepository;
    public List<DetailsOrders> getByIdOrder(String idOrders){
        return detailsOrdersRepository.findAll().stream().filter(s -> s.getIdOrders().getIdOrders().equals(idOrders)).toList();
    }

    @Transactional
    public void addItemToDetailsOrders(CartItems cartItems, Orders orders) throws ExecutionException {
        DetailsOrders detailsOrders = new DetailsOrders();
        detailsOrders.setIdOrders(orders);
        detailsOrders.setQuantity(cartItems.getQuantity());
        detailsOrders.setIdFertilizer(cartItems.getIdFertilizer());
        try {
            detailsOrdersRepository.save(detailsOrders);
            editQuantity(cartItems.getIdFertilizer(), cartItems.getQuantity());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExecutionException(e.getMessage(), e);
        }
    }

    public void editQuantity(Fertilizer fertilizer, int quantity) throws ExecutionException {
        fertilizer.setNums(fertilizer.getNums() - quantity);
        fertilizerRepository.save(fertilizer);
    }
    public List<DetailsOrders> getAll(){
        return detailsOrdersRepository.findAll();
    }
}
