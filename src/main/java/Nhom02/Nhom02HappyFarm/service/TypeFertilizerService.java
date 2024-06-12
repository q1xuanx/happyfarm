package Nhom02.Nhom02HappyFarm.service;

import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.entities.TypeFertilizer;
import Nhom02.Nhom02HappyFarm.repository.FertilizerRepository;
import Nhom02.Nhom02HappyFarm.repository.TypeFertilizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeFertilizerService {

    private final TypeFertilizerRepository typeFer;

    public List<TypeFertilizer> GetAllTypeFertilizer(String name){
        if(name == null){
            return typeFer.findAll();
        }
        return typeFer.findAll().stream().filter(typename -> typename.getNameTypeFertilizer().contains(name)).collect(Collectors.toList());
    }


    public void AddOrEditTypeFertilizer(TypeFertilizer typeFertilizer){
        typeFer.save(typeFertilizer);
    }


    public TypeFertilizer GetTypeFertilizer(String id){
        Optional<TypeFertilizer> type = typeFer.findById(id);
        return type.orElseThrow(() -> new NoSuchElementException("No typerfertilizer with " + id + " exist !"));
    }


    public List<TypeFertilizer> GetTypeFertilizerNotDelete(){
        return typeFer.findByIsDelete(false);
    }


    public List<TypeFertilizer> GetTypeByName(String name){
        return typeFer.findByNameTypeFertilizer(name);
    }

    public void DeleteTypeFertilizer(String id){
        TypeFertilizer type = typeFer.findById(id).orElseThrow(() -> new NoSuchElementException("No typerfertilizer with " + id + " exist !"));
        type.setDelete(true);
        typeFer.save(type);
    }
}
