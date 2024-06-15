package Nhom02.Nhom02HappyFarm.dto.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
//@JsonInclude(JsonInclude.Include.NON_NULL) // nếu null thì khi response sẽ ko hiển thị trường dữ liệu lên
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiReponse<h> {
    int code ;
    String message;
    h result;
}
