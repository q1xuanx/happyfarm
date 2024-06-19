package Nhom02.Nhom02HappyFarm.service;


import Nhom02.Nhom02HappyFarm.entities.Blog;
import Nhom02.Nhom02HappyFarm.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blog;

    public List<Blog> getAllBlog(){
        return blog.findAll();
    }
    public Blog getBlog(String id){
        Optional<Blog> get = blog.findById(id);
        return get.orElseThrow(() -> new NoSuchElementException("Blog with id " + id + " doesnt exist"));
    }
    public void createOrSaveBlog(Blog blog){
        LocalDate date = LocalDate.now();
        blog.setTimeCreate(Date.valueOf(date));
        this.blog.save(blog);
    }
    public void deleteBlog(String id){
        this.blog.deleteById(id);
    }
}
