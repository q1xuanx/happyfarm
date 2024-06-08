package Nhom02.Nhom02HappyFarm.service;


import Nhom02.Nhom02HappyFarm.entities.Brand;
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
    public List<Brand> GetAllOriginNotDelete(){
        return brandRepository.findByIsDelete(false);
    }

    public void DeleteBrand (String id){
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No brand with " + id + " exist !"));
        brand.setDelete(true);
        brandRepository.save(brand);
    }
    public List<Brand> findByName(String name){
        return brandRepository.findByNameBrand(name);
    }
}
