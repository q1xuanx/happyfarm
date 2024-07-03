package Nhom02.Nhom02HappyFarm.service;


import Nhom02.Nhom02HappyFarm.entities.Brand;
import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.entities.OriginFertilizer;
import Nhom02.Nhom02HappyFarm.repository.BrandRepository;
import Nhom02.Nhom02HappyFarm.repository.OriginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final String brand_none = "Không có";
    private final FertilizerService fertilizerService;
    public List<Brand> GetAllBrand(String name){
        if(name == null){
            return brandRepository.findAll();
        }
        return brandRepository.findAll().stream().filter(typename -> typename.getNameBrand().contains(name)).collect(Collectors.toList());
    }
    public void AddOrEditBrand(Brand brand){
        brandRepository.save(brand);
    }
    public Brand GetBrand(String id){
        Optional<Brand> brand = brandRepository.findById(id);
        return brand.orElseThrow(() -> new NoSuchElementException("No brand with " + id + " exist !"));
    }
    public List<Brand> GetAllBrandNotDelete(){
        return brandRepository.findByIsDelete(false);
    }
    public List<Brand> GetAllBrandDelete() {return brandRepository.findByIsDelete(true);}

    public void DeleteBrand (String id){
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No brand with " + id + " exist !"));
        brand.setDelete(true);
        brandRepository.save(brand);
    }
    public int ConfirmDelete(String id){
        try {
            Brand brand = brandRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found"));
            Optional<Brand> br = brandRepository.findAll().stream().filter(s -> s.getNameBrand().equals(brand_none)).findFirst();
            if (br.isEmpty()) {
                return 0;
            }
            Brand br1 = br.get();
            List<Fertilizer> updateList = fertilizerService.FertilizerNotDelete().stream().filter(s -> s.getBrandName().getIdBrand().equals(id)).toList();
            for (Fertilizer fertilizer : updateList) {
                fertilizer.setBrandName(br1);
                fertilizerService.EditFertilizer(fertilizer);
            }
            brandRepository.deleteById(id);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    public void checkValidName(String name){
        if (brandRepository.findAll().stream().noneMatch(s -> s.getNameBrand().equals(name))){
            Brand brand = new Brand();
            brand.setNameBrand(brand_none);
            brandRepository.save(brand);
        }
    }

    public List<Brand> findByName(String name){
        return brandRepository.findByNameBrand(name);
    }
}
