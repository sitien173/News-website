package ptit.ltw.Service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FirebaseImageService {
    String getImageUrl(String name);

    String save(MultipartFile file) throws IOException;


    void delete(String name) throws IOException;

    byte [] getFile(String path) throws IOException;
    default String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    default String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }

}
