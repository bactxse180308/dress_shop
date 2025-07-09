package hsf302.assignment.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init();
    String store(MultipartFile file);
}