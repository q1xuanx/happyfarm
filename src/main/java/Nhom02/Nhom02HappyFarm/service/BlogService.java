package Nhom02.Nhom02HappyFarm.service;


import Nhom02.Nhom02HappyFarm.entities.Blog;
import Nhom02.Nhom02HappyFarm.repository.BlogRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blog;
    private final UsersService user;
    private final FertilizerService fertilizerService;
    private final Cloudinary cloudinary;
    public List<Blog> getAllBlog(){
        return blog.findAll();
    }

    public Blog getBlog(String id){
        Optional<Blog> get = blog.findById(id);
        return get.orElseThrow(() -> new NoSuchElementException("Blog with id " + id + " doesnt exist"));
    }

    public CompletableFuture<String> uploadImageAsync(MultipartFile file) throws IOException {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                return UploadImage(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public String UploadImage(MultipartFile image) throws IOException {
        return cloudinary.uploader().upload(image.getBytes(), Map.of()).get("url").toString();
    }

    public void createOrSaveBlog(Blog blog) throws IOException, ExecutionException, InterruptedException {
        LocalDate date = LocalDate.now();
        if (blog.getImage() != null) {
            if (!blog.getImagePresent().isEmpty()){
                Map delete = deleteImage(blog.getImagePresent());
            }
            CompletableFuture<String> uploaded = uploadImageAsync(blog.getImage());
            blog.setImagePresent(uploaded.get());
        }
        blog.setTimeCreate(Date.valueOf(date));
        blog.setUrl(fertilizerService.generateUrl(blog.getTitle()));
        this.blog.save(blog);       
    }
    public String editImage(MultipartFile img) throws IOException, ExecutionException, InterruptedException {
        CompletableFuture<String> uploaded = uploadImageAsync(img);
        return uploaded.get();
    }
    public Map deleteImage(String url) throws IOException {
        String publicId = splitToGetId(url);
        Map delete = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        return delete;
    }
    public String splitToGetId(String url){
        String[] splitUrl = url.split("/");
        return splitUrl[splitUrl.length - 1].split("\\.")[0];
    }
    public void deleteBlog(String id){
        this.blog.deleteById(id);
    }
    public String checkExists(Blog blog) throws Exception {
        if (blog.getTitle().isEmpty()){
            return "Title null ";
        }else if (blog.getDetails().isEmpty()){
            return "Details null";
        }else if (user.GetUser(blog.getUserCreate().getIdUser()) == null){
            return "Not found user";
        }
        return "OK";
    }
    public boolean checkNameExist(String title){
        return this.blog.findAll().stream().anyMatch(s->s.getTitle().equals(title));
    }
    public Blog getBlogByUrl(String url){
        return blog.findAll().stream().filter(s->s.getUrl().equals(url)).findFirst().get();
    }
}
