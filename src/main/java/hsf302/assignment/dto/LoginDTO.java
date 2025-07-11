package hsf302.assignment.dto;
import jakarta.validation.constraints.*;

public class LoginDTO {
    @NotBlank(message = "Vui lòng nhập email hoặc số điện thoại")
    private String email;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    private String password;

    // ... getter/setter
}
