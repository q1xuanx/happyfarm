package Nhom02.Nhom02HappyFarm.service;


import Nhom02.Nhom02HappyFarm.entities.Blog;
import Nhom02.Nhom02HappyFarm.repository.BlogRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    //toDo: need to add Crud in this.
}
