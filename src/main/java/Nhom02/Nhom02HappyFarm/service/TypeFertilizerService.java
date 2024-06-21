package Nhom02.Nhom02HappyFarm.service;

import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.entities.TypeFertilizer;
import Nhom02.Nhom02HappyFarm.repository.FertilizerRepository;
import Nhom02.Nhom02HappyFarm.repository.TypeFertilizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeFertilizerService {
    private final TypeFertilizerRepository typeFer;

    public List<TypeFertilizer> GetAllTypeFertilizer(String name) throws Exception {
        try {
            if(name == null){
                return typeFer.findAll();
            }
            return typeFer.findAll().stream().filter(typename -> typename.getNameTypeFertilizer().contains(name)).collect(Collectors.toList());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void AddOrEditTypeFertilizer(TypeFertilizer typeFertilizer) throws Exception {
        try {
            typeFer.save(typeFertilizer);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public TypeFertilizer GetTypeFertilizer(String id) throws Exception {
        try {
            Optional<TypeFertilizer> type = typeFer.findById(id);
            return type.orElseThrow(() -> new NoSuchElementException("No typerfertilizer with " + id + " exist !"));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<TypeFertilizer> GetTypeFertilizerNotDelete() throws Exception {
        try {
            return typeFer.findByIsDelete(false);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<TypeFertilizer> GetTypeByName(String name) throws Exception{
        try {
            return typeFer.findByNameTypeFertilizer(name);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void DeleteTypeFertilizer(String id) throws Exception {
        try {
            TypeFertilizer type = typeFer.findById(id).orElseThrow(() -> new NoSuchElementException("No typerfertilizer with " + id + " exist !"));
            type.setDelete(true);
            typeFer.save(type);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
