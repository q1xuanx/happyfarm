package Nhom02.Nhom02HappyFarm.service;


import Nhom02.Nhom02HappyFarm.entities.Ratings;
import Nhom02.Nhom02HappyFarm.repository.RatingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class RatingsService {
    private final RatingsRepository ratingsRepository;

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
}
