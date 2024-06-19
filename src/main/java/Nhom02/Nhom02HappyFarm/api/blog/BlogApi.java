package Nhom02.Nhom02HappyFarm.api.blog;


import Nhom02.Nhom02HappyFarm.entities.Blog;
import Nhom02.Nhom02HappyFarm.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/blog")
@Api(value = "Api blog")
public class BlogApi {
    private final BlogService blogService;

    @ApiOperation(value = "Lay 1 list cac blog")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lay thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay")
    })
    @GetMapping("/getlist")
    public ResponseEntity<List<Blog>> getList(){
        try {
            return new ResponseEntity<>(blogService.getAllBlog(), HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @ApiOperation(value = "Tao 1 blog moi de them ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay")
    })
    @GetMapping("/addblog")
    public ResponseEntity<Blog> addNewBlog(){
        try {
            return new ResponseEntity<>(new Blog(), HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @ApiOperation(value = "Them moi 1 blog")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay")
    })
    @PostMapping("/addblog")
    public ResponseEntity<Blog> addNewBlog(@RequestBody Blog blog){
        try {
            blogService.createOrSaveBlog(blog);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @ApiOperation(value = "Lay blog theo {id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay")
    })
    @GetMapping("/getblog/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable String id){
        try{
            return new ResponseEntity<>(blogService.getBlog(id), HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @ApiOperation(value = "Edit blog theo {id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay")
    })
    @PutMapping("/edit/{id}")
    public ResponseEntity<Blog> editBlog(@PathVariable String id, @RequestBody Blog blog){
        try{
            blogService.createOrSaveBlog(blog);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @ApiOperation(value = "Xoa blog theo {id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Blog> deleteBlog(@PathVariable String id){
        try{
            blogService.deleteBlog(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
