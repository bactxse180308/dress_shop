package hsf302.assignment.service.impl;

import hsf302.assignment.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public FileSystemStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir);
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        // 1. Kiểm tra loại file (đã được cải tiến)
        if (!isImageFile(file)) {
            throw new RuntimeException("Lỗi: Chỉ cho phép upload file ảnh có định dạng .jpg, .jpeg, .png, .gif, .bmp");
        }

        // 2. Chuẩn hóa tên file
        String originalFilename = file.getOriginalFilename();
        String sanitizedFilename = sanitizeFilename(originalFilename);
        String uniqueFilename = System.currentTimeMillis() + "_" + sanitizedFilename;

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, this.rootLocation.resolve(uniqueFilename),
                    StandardCopyOption.REPLACE_EXISTING);
            return uniqueFilename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + uniqueFilename, e);
        }
    }

    // ===== PHƯƠNG THỨC ĐÃ ĐƯỢC CẢI TIẾN =====
    private boolean isImageFile(MultipartFile file) {
        if (file == null || file.getContentType() == null) {
            return false;
        }

        // Tạo một danh sách các ContentType ảnh được chấp nhận
        List<String> allowedTypes = Arrays.asList(
                "image/jpeg",
                "image/png",
                "image/gif",
                "image/bmp"
        );

        // Kiểm tra xem ContentType của file có nằm trong danh sách không
        return allowedTypes.contains(file.getContentType().toLowerCase());
    }

    private String sanitizeFilename(String filename) {
        if (filename == null) {
            return "unknown-file";
        }
        String name = filename;
        String extension = "";
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0) {
            name = filename.substring(0, dotIndex);
            extension = filename.substring(dotIndex);
        }
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String noDiacritics = pattern.matcher(normalized).replaceAll("");
        String slug = noDiacritics.toLowerCase()
                .replaceAll("\\s+", "-")
                .replaceAll("[^a-z0-9\\-]", "");
        return slug + extension;
    }
}