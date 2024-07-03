package Nhom02.Nhom02HappyFarm.service;


import Nhom02.Nhom02HappyFarm.entities.Banner;
import Nhom02.Nhom02HappyFarm.repository.BannerRepository;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;
    private final Cloudinary cloudinary;
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
    public void addOrEditBanner(Banner banner) throws IOException, ExecutionException, InterruptedException {
        if (banner.getImage() != null){
            CompletableFuture<String> image = uploadImageAsync(banner.getImage());
            banner.setImageFile(image.get());
        }
        bannerRepository.save(banner);
    }
    public void deleteBanner(String id){
        bannerRepository.deleteById(id);
    }
    public Banner getBanner(String id){
        return bannerRepository.findById(id).get();
    }
    public List<Banner> getAllBanner(){
        return bannerRepository.findAll();
    }
}
