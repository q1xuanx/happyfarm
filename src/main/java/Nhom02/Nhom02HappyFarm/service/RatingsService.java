package Nhom02.Nhom02HappyFarm.service;


import Nhom02.Nhom02HappyFarm.entities.DetailsOrders;
import Nhom02.Nhom02HappyFarm.entities.Orders;
import Nhom02.Nhom02HappyFarm.entities.Ratings;
import Nhom02.Nhom02HappyFarm.repository.RatingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;


@Service
@RequiredArgsConstructor
public class RatingsService {
    private final RatingsRepository ratingsRepository;
    private final OrdersService orders;
    private final DetailsOrderService detailsOrder;
    public Ratings getRatingsById(String id) throws Exception {
        try {
            return ratingsRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found " + id));
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<Ratings> listRatings() throws Exception{
        try{
            return ratingsRepository.findAll();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String checkExist(Ratings rate){
        if (rate.getPoints() == 0){
            return "Points not found";
        }else if (rate.getComments().isEmpty()){
            return "Commnets not found";
        }
        return "OK";
    }

    public boolean checkOrderOrNot(String idUser, String nameFer){
        List<Orders> listOrder = orders.getHistoryOrder(idUser);
        for(Orders orders1 : listOrder){
            Optional<DetailsOrders> det = detailsOrder.getByIdOrder(orders1.getIdOrders()).stream().filter(s -> s.getNameFertilizer().equals(nameFer)).findFirst();
            if(det.isPresent()) return true;
        }
        return false;
    }

    public Map<Integer, Integer> totalRatings(String idFer){
        Map<Integer, Integer> countRatings = new HashMap<>();
        countRatings.put(1, 0);
        countRatings.put(2, 0);
        countRatings.put(3, 0);
        countRatings.put(4, 0);
        countRatings.put(5, 0);
        List<Ratings> list = ratingsRepository.findAll().stream().filter(s->s.getIdFertilizer().getIdFertilizer().equals(idFer)).toList();
        for(Ratings rate : list){
            countRatings.put(rate.getPoints(), countRatings.get(rate.getPoints()) + 1);
        }
        return countRatings;
    }

    public void addNewOrEdit(Ratings ratings) throws Exception {
        try{
            ratingsRepository.save(ratings);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void deleteRatingsById(String id) throws Exception {
        try{
            ratingsRepository.deleteById(id);
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<Ratings> getListByIdFer(String id) throws Exception {
        try{
            return ratingsRepository.findAll().stream().filter(s -> s.getIdFertilizer().getIdFertilizer().equals(id) && !s.getIdFertilizer().isDelete()).toList();
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public float avgPointRatings(String id){
        List<Ratings> listRating = ratingsRepository.findAll().stream().filter(s -> s.getIdFertilizer().getIdFertilizer().equals(id)).toList();
        float avg = 0;
        for(Ratings rate : listRating){
            avg += rate.getPoints();
        }
        return (avg / listRating.size());
    }

}
