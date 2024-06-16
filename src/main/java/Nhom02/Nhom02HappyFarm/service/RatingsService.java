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

    public Ratings getRatingsById(String id) {
        return ratingsRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found " + id));
    }

    public List<Ratings> listRatings(){
        return ratingsRepository.findAll();
    }

    public void addNewOrEdit(Ratings ratings) {
        ratingsRepository.save(ratings);
    }

    public void deleteRatingsById(String id) {
        ratingsRepository.deleteById(id);
    }

    public List<Ratings> getListByIdFer(String id){
        return ratingsRepository.findAll().stream().filter(s -> s.getIdFertilizer().getIdFertilizer().equals(id) && s.getIdFertilizer().isDelete()).toList();
    }
}
