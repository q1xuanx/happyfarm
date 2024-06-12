package Nhom02.Nhom02HappyFarm.service;

import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.repository.FertilizerRepository;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.channels.MulticastChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FertilizerService {
    private final Cloudinary cloudinary;
    private final FertilizerRepository fertilizerRepository;


    public List<Fertilizer> listFertilizer(){
        return fertilizerRepository.findAll();
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
