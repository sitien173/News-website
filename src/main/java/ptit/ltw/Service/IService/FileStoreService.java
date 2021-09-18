package ptit.ltw.Service.IService;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.Collection;

public interface FileStoreService {
    String upload(MultipartFile multipartFile) throws FileNotFoundException;
    Resource load(String fileName);
    boolean remove(String url);
    void remove(Collection<String> urls);
}
