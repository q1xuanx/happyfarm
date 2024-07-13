package Nhom02.Nhom02HappyFarm.api.blog;


import Nhom02.Nhom02HappyFarm.entities.Blog;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Expectation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/blog")
@Api(value = "Api blog")
@CrossOrigin(origins = "*")
public class BlogApi {
    private final BlogService blogService;
    private final ResponseHandler responseHandler;
    @ApiOperation(value = "Lay 1 list cac blog")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lay thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay")
    })
    @GetMapping("/getlist")
    public ResponseEntity<Object> getList(){
        try {
            return ResponseEntity.ok(responseHandler.successResponse("Get list success", blogService.getAllBlog()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Tao 1 blog moi de them ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay")
    })
    @GetMapping("/addblog")
    public ResponseEntity<Object> addNewBlog(){
        try {
            return ResponseEntity.ok(responseHandler.successResponse("Create success", new Blog()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Lay blog boi url")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay")
    })
    @GetMapping("/blogget/{url}")
    public ResponseEntity<Object> getBlogByURL(@PathVariable String url){
        try {
            Blog blog = blogService.getBlogByUrl(url);
            if (blog == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Get success", blog));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Xoa 1 hinh trong anh")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay")
    })
    @DeleteMapping("/deleteimage")
    public ResponseEntity<Object> deleteImage(@RequestParam String id){
        try {
            Blog blog = blogService.getBlog(id);
            if (blog == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            if (blog.getImagePresent().equals("Deleted")){
                return ResponseEntity.ok().body(responseHandler.successResponseButNotHaveContent("Hinh anh da bi xoa"));
            }
            Map deleted = blogService.deleteImage(blog.getImagePresent());
            blog.setImagePresent("Deleted");
            blogService.createOrSaveBlog(blog);
            return ResponseEntity.ok(responseHandler.successResponse("Deleted", deleted));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }

    @ApiOperation(value = "Chinh sua hinh anh")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay")
    })
    @PutMapping("/editimage")
    public ResponseEntity<Object> editImage(@RequestParam String id, @ModelAttribute MultipartFile imageReplace){
        try {
            Blog blog = blogService.getBlog(id);
            if (blog == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            if(imageReplace != null){
                Map deleted = blogService.deleteImage(blog.getImagePresent());
                blog.setImagePresent(blogService.editImage(imageReplace));
                blogService.createOrSaveBlog(blog);
                return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Thay anh thanh cong"));
            }
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Không có gì để thay đổi"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Them moi 1 blog, request body la 1 blog")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay")
    })

    @PostMapping("/addblog")
    public ResponseEntity<Object> addNewBlog(@ModelAttribute Blog blog){
        try {
            if(!blogService.checkExists(blog).equals("OK")){
                return ResponseEntity.badRequest().body(responseHandler.failResponse(blogService.checkExists(blog)));
            }else {
                if (blogService.checkNameExist(blog.getTitle())){
                    return ResponseEntity.badRequest().body(responseHandler.failResponse("Title exist"));
                }
            }
            blogService.createOrSaveBlog(blog);
            return ResponseEntity.ok(responseHandler.successResponseButNotHaveContent("Tao moi thanh cong"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Lay blog theo {id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay")
    })
    @GetMapping("/getblog/{id}")
    public ResponseEntity<Object> getBlogById(@PathVariable String id){
        try{
            if (blogService.getBlog(id) == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            return ResponseEntity.ok(responseHandler.successResponse("Tim blog thanh cong", blogService.getBlog(id)));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Edit blog theo {id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay")
    })
    @PutMapping("/edit")
    public ResponseEntity<Object> editBlog(@ModelAttribute Blog blog){
        try{
            Blog check = blogService.getBlog(blog.getIdBlog());
            if (check == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            if (!blogService.checkExists(blog).equals("OK")){
                return ResponseEntity.badRequest().body(responseHandler.failResponse(blogService.checkExists(blog)));
            }
            blogService.createOrSaveBlog(blog);
            return ResponseEntity.ok(responseHandler.successResponse("Edit thanh cong", blog));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
    @ApiOperation(value = "Xoa blog theo {id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteBlog(@PathVariable String id){
        try{
            if(blogService.getBlog(id) == null){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Not found"));
            }
            blogService.deleteBlog(id);
            return ResponseEntity.ok(responseHandler.successResponse("Xoa thanh cong", id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(responseHandler.failResponse(e.getMessage()));
        }
    }
}
