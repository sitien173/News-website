package ptit.ltw.Service.ServiceImpl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ptit.ltw.Service.FirebaseImageService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class  FirebaseImageServiceImpl implements FirebaseImageService {

    private final Environment environment;

    @PostConstruct
    public void init() {
        // initialize Firebase
        try {
            ClassPathResource serviceAccount = new ClassPathResource("firebase.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setStorageBucket(environment.getProperty("firebase.bucket-name"))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public byte[] getFile(String path) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        if (StringUtils.isEmpty(path)) {
            throw new IOException("invalid file name");
        }
        Blob blob = bucket.get(path);
        if (blob == null) {
            throw new IOException("file not found");
        }
        return blob.getContent();
    }

    @Override
    public String save(MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        String fileUploaded = generateFileName(file.getOriginalFilename());
        bucket.create(fileUploaded, file.getBytes(), file.getContentType());
        return fileUploaded;
    }

    @Async
    @Override
    public void delete(String name) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        if (StringUtils.isEmpty(name)) {
            throw new IOException("invalid file name");
        }
        Blob blob = bucket.get(name);
        if (blob == null) {
            throw new IOException("file not found");
        }
        blob.delete();
    }
}
