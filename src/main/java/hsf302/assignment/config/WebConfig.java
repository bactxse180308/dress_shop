package hsf302.assignment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cú pháp này đơn giản và hoạt động ổn định trên mọi hệ điều hành.
        // "file:./" nghĩa là "lấy từ thư mục hiện tại của ứng dụng"
        registry.addResourceHandler("/" + uploadDir + "/**")
                .addResourceLocations("file:./" + uploadDir + "/");
    }
}