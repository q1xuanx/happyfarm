package Nhom02.Nhom02HappyFarm.service;

import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.repository.FertilizerRepository;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FertilizerService {
    private final Cloudinary cloudinary;
    private final FertilizerRepository fertilizerRepository;
    private final int sizeOfPage = 10;
    public List<Fertilizer> listFertilizer(){
        int getTotalItems = fertilizerRepository.findAll().size();
        Pageable page = PageRequest.of(((getTotalItems + sizeOfPage - 1) / sizeOfPage) - 1 , sizeOfPage);
        Page<Fertilizer> paged = fertilizerRepository.findAll(page);
        return paged.getContent();
    }

    public void addNew(Fertilizer fertilizer) throws IOException {
        try {
            CompletableFuture<String> getImage = uploadImageAsync(fertilizer.getFileImageRepresent());
            List<CompletableFuture<String>> listImageOptional = new ArrayList<>();
            for (MultipartFile image : fertilizer.getFileImageOptional()) {
                listImageOptional.add(uploadImageAsync(image));
            }
            fertilizer.setImageRepresent(getImage.get());
            fertilizer.setImageOptional(listImageOptional.stream().map(CompletableFuture::join).collect(Collectors.toList()));
            fertilizerRepository.save(fertilizer);
        }catch(Exception e){
            throw new IOException(e.getMessage());
        }
    }
    public Fertilizer GetFertilizer(String id) throws IOException {
        try {
            Optional<Fertilizer> fertilizer = fertilizerRepository.findById(id);
            return fertilizer.orElseThrow(() -> new NoSuchElementException("Not found " + id));
        }catch (Exception ex){
            throw new IOException(ex);
        }
    }
    public void EditFertilizer(Fertilizer fertilizer) throws ExecutionException {
        try {
            if (fertilizer.getFileImageRepresent() != null) {
                CompletableFuture<String> getNewRepresent = uploadImageAsync(fertilizer.getFileImageRepresent());
                fertilizer.setImageRepresent(getNewRepresent.get());
            }
            if (fertilizer.getFileImageOptional() != null){
                List<CompletableFuture<String>> listImageOptional = new ArrayList<>();
                for(MultipartFile image : fertilizer.getFileImageOptional()){
                    listImageOptional.add(uploadImageAsync(image));
                }
                fertilizer.setImageOptional(listImageOptional.stream().map(CompletableFuture::join).collect(Collectors.toList()));
            }
            fertilizerRepository.save(fertilizer);
        }catch(Exception ex){
            throw new ExecutionException(ex);
        }
    }

    public void DeleteFertilizer(String id) throws ExecutionException {
        try {
            Optional<Fertilizer> fertilizer = fertilizerRepository.findById(id);
            if (fertilizer.isPresent()) {
                fertilizer.get().setDelete(true);
                fertilizerRepository.save(fertilizer.get());
            }
        }catch (Exception ex){
            throw new ExecutionException(ex);
        }
    }
    //Tìm phân bón qua tên
    public List<Fertilizer> findFertilizerByName(String name){
        Specification<Fertilizer> spec = Specification.where(FertilizerSpecifiation.hasName(name)).and(FertilizerSpecifiation.isNotDelete());
        Pageable page = PageRequest.of(0, sizeOfPage);
        Page<Fertilizer> paged = fertilizerRepository.findAll(spec,page);
        return paged.getContent();
    }
    //Lọc các loại phân bón
    public List<Fertilizer> filter(String brand, String origin, String typeFer){
        Specification<Fertilizer> spec = Specification.where(FertilizerSpecifiation.hasBrand(brand)).and(FertilizerSpecifiation.hasOrigin(origin)).and(FertilizerSpecifiation.hasType(typeFer)).and(FertilizerSpecifiation.isNotDelete());
        Pageable page = PageRequest.of( 0, sizeOfPage);
        Page<Fertilizer> paged = fertilizerRepository.findAll(spec,page);
        return paged.getContent();
    }
    //Tìm phân bón chưa delete
    public List<Fertilizer> FertilizerNotDelete(){
        Specification<Fertilizer> spec = Specification.where(FertilizerSpecifiation.isNotDelete());
        Pageable page = PageRequest.of(0 , sizeOfPage);
        Page<Fertilizer> paged = fertilizerRepository.findAll(spec,page);
        return paged.getContent();
    }
    //Xử lý đa luồng
    public CompletableFuture<String> uploadImageAsync(MultipartFile file) throws IOException {
        return CompletableFuture.supplyAsync(() ->
        {
            try{
                return UploadImage(file);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        });
    }
    public String UploadImage(MultipartFile image) throws IOException {
        return cloudinary.uploader().upload(image.getBytes(), Map.of()).get("url").toString();
    }
}
