package Nhom02.Nhom02HappyFarm.service;

import Nhom02.Nhom02HappyFarm.entities.Brand;
import Nhom02.Nhom02HappyFarm.entities.OriginFertilizer;
import Nhom02.Nhom02HappyFarm.entities.TypeFertilizer;
import Nhom02.Nhom02HappyFarm.repository.OriginRepository;
import Nhom02.Nhom02HappyFarm.repository.TypeFertilizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OriginService {
    private final OriginRepository origin;
    public List<OriginFertilizer> GetAllOriginFertilizer(String name){
        if(name == null){
            return origin.findAll();
        }
        return origin.findAll().stream().filter(typename -> typename.getNameOrigin().contains(name)).collect(Collectors.toList());
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

    public void DeleteOriginFertilizer (String id){
        OriginFertilizer type = origin.findById(id).orElseThrow(() -> new NoSuchElementException("No origin with " + id + " exist !"));
        type.setDelete(true);
        origin.save(type);
    }
    public List<OriginFertilizer> findByName(String name){
        return origin.findByNameOrigin(name);
    }
}
