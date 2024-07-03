package Nhom02.Nhom02HappyFarm.api.blog;


import Nhom02.Nhom02HappyFarm.entities.Blog;
import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    @ApiOperation(value = "Them moi 1 blog, request body la 1 blog")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Thanh cong"),
            @ApiResponse(code = 400, message = "Co loi xay ra trong qua trinh lay")
    })
    @PostMapping("/addblog")
    public ResponseEntity<Object> addNewBlog(@ModelAttribute Blog blog){
        try {
            if(blog.getDetails().isEmpty()){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Vui long nhap chi tiet blog"));
            }else if (blog.getTitle().isEmpty()){
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Vui long nhap tieu de"));
            }else if(blog.getUserCreate() == null) {
                return ResponseEntity.badRequest().body(responseHandler.failResponse("Khong tim thay idUser"));
            }else {
                Optional<Blog> check = blogService.getAllBlog().stream().filter(s -> s.getTitle().equals(blog.getTitle())).findFirst();
                if (check.isPresent()){
                    return ResponseEntity.badRequest().body(responseHandler.failResponse("Tieu de bai viet bi trung"));
                }
            }
            blogService.createOrSaveBlog(blog);
            return ResponseEntity.ok(responseHandler.successResponse("Tao moi thanh cong", blog));
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
