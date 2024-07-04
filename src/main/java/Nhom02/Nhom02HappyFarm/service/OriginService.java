package Nhom02.Nhom02HappyFarm.service;

import Nhom02.Nhom02HappyFarm.entities.Brand;
import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.entities.OriginFertilizer;
import Nhom02.Nhom02HappyFarm.entities.TypeFertilizer;
import Nhom02.Nhom02HappyFarm.repository.OriginRepository;
import Nhom02.Nhom02HappyFarm.repository.TypeFertilizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OriginService {
    private final OriginRepository origin;
    private final String origin_none = "Không có";
    private final FertilizerService fertilizerService;
    public List<OriginFertilizer> GetAllOriginFertilizer(String name){
        if(name == null){
            return origin.findAll();
        }
        return origin.findAll().stream().filter(typename -> typename.getNameOrigin().contains(name)).collect(Collectors.toList());
    }

    public void checkValidName(String name){
        if (origin.findAll().stream().noneMatch(s -> s.getNameOrigin().equals(name))){
            OriginFertilizer origin = new OriginFertilizer();
            origin.setNameOrigin(origin_none);
            this.origin.save(origin);
        }
    }
    public void AddOrEditOriginFertilizer(OriginFertilizer originFertilizer){
        origin.save(originFertilizer);
    }

    public OriginFertilizer GetOriginFertilizer(String id){
        Optional<OriginFertilizer> type = origin.findById(id);
        return type.orElseThrow(() -> new NoSuchElementException("No origin with " + id + " exist !"));
    }

    public List<OriginFertilizer> GetAllOriginNotDelete(){
        return origin.findByisDelete(false);
    }
    public List<OriginFertilizer> GetAllOriginDelete(){ return origin.findByisDelete(true);}

    //Hidding
    public void DeleteOriginFertilizer (String id){
        OriginFertilizer type = origin.findById(id).orElseThrow(() -> new NoSuchElementException("No origin with " + id + " exist !"));
        type.setDelete(true);
        origin.save(type);
    }

    public int confirmDelete(String id) throws ExecutionException {
        OriginFertilizer originFertilizer = origin.findById(id).orElseThrow(() -> new NoSuchElementException("No origin with " + id + " exist !"));
        Optional<OriginFertilizer> findNameNone = origin.findAll().stream().filter(s -> s.getNameOrigin().equals(origin_none)).findFirst();
        if (findNameNone.isEmpty()){
            return 0;
        }
        OriginFertilizer getOriginNameNone = findNameNone.get();
        List<Fertilizer> listFertilizerNeedUpdate = fertilizerService.FertilizerNotDelete().stream().filter(s -> s.getOriginFer().getIdOrigin().equals(id)).toList();
        for (Fertilizer fer : listFertilizerNeedUpdate){
            fer.setOriginFer(getOriginNameNone);
            fertilizerService.EditFertilizer(fer);
        }
        origin.deleteById(id);
        return 1;
    }

    public boolean existName(String name){
        return origin.findAll().stream().anyMatch(s -> s.getNameOrigin().equals(name));
    }

    public List<OriginFertilizer> findByName(String name){
        return origin.findByNameOrigin(name);
    }
}
