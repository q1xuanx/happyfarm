package Nhom02.Nhom02HappyFarm.service;

import Nhom02.Nhom02HappyFarm.entities.Brand;
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
    private final String unknow = "Không có";
    private final FertilizerService fertilizerService;
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
    public List<TypeFertilizer> GetTypeFertilizerDelete() throws Exception {
        try {
            return typeFer.findByIsDelete(true);
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
    //Hiding
    public void DeleteTypeFertilizer(String id) throws Exception {
        try {
            TypeFertilizer type = typeFer.findById(id).orElseThrow(() -> new NoSuchElementException("No typerfertilizer with " + id + " exist !"));
            type.setDelete(true);
            typeFer.save(type);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public int ConfirmDelete(String id){
        try {
            TypeFertilizer typfer = typeFer.findById(id).orElseThrow(() -> new NoSuchElementException("Not found"));
            Optional<TypeFertilizer> type = typeFer.findAll().stream().filter(s -> s.getNameTypeFertilizer().equals(unknow)).findFirst();
            if (type.isEmpty()) {
                return 0;
            }
            TypeFertilizer type1 = type.get();
            List<Fertilizer> updateList = fertilizerService.FertilizerNotDelete().stream().filter(s -> s.getType().getIdTypeFertilizer().equals(id)).toList();
            for (Fertilizer fertilizer : updateList) {
                fertilizer.setType(type1);
                fertilizerService.EditFertilizer(fertilizer);
            }
            this.typeFer.deleteById(id);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
    public void checkValidName(String name){
        if (typeFer.findAll().stream().noneMatch(s -> s.getNameTypeFertilizer().equals(name))){
            TypeFertilizer tyoe = new TypeFertilizer();
            tyoe.setNameTypeFertilizer(unknow);
            typeFer.save(tyoe);
        }
    }
    public boolean existName(String name){
        return typeFer.findAll().stream().anyMatch(s -> s.getNameTypeFertilizer().equals(name));
    }
}
