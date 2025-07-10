package hsf302.assignment.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.List;

public interface StorageService {
    void init();
    String store(MultipartFile file);
    List<String> store(MultipartFile[] files);
}