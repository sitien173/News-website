package ptit.ltw.Service.ServiceImpl;

import com.cksource.ckfinder.exception.FileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ptit.ltw.Service.IService.FileStoreService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileStoreServiceImpl implements FileStoreService {
    public static String FILE_STORAGE_ROOT = getRootPath();
    private static String getRootPath(){
        /*try {
            Resource resource = new ClassPathResource("/static/assets");
            return resource.getFile().getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;*/
        Path path = Paths.get(System.getProperty("user.dir"),"src/main/resources/static/assets");
        return path.toAbsolutePath().toString();
    }
    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }
    private String generateFileName(String originalFileName) {
        // replace <:> tránh InvalidPathException
        return originalFileName.substring(0,originalFileName.lastIndexOf(".")) + LocalDateTime.now().toString().replaceAll(":","-") + "." + getExtension(originalFileName);
    }

    private boolean isAllow(String fileName){
        String[] allowFiles = {".gif",".jfif",".png",".jpg",".jpeg",".bpm",".svg"};
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        List<String> suffixList = Arrays.stream(allowFiles).collect(Collectors.toList());
        return suffixList.contains(suffix);
    }

    @Override
    public String upload(MultipartFile multipartFile)  {
        if(multipartFile.isEmpty())
            throw  new IllegalStateException("File is Empty");
        else if(!isAllow(multipartFile.getOriginalFilename()))
            throw new IllegalStateException("File not accept.Support for extension [gif | jfif | png | jpg | jpeg | bpm | svg]");

        String storageRoot = FILE_STORAGE_ROOT+ File.separator+"img";
        String fileName = generateFileName(multipartFile.getOriginalFilename());
        File file = new File(storageRoot+File.separator+fileName);

        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(file);
        }catch (IOException e){
            e.printStackTrace();
        }
        return fileName;
    }

    public static Path findPath(Path path,String fileName) throws IOException {
        try (Stream<Path> walk = Files.walk(path)) {
            return walk.filter(Files::isRegularFile)
                    .filter(path1 -> path1.getFileName().toString().equalsIgnoreCase(fileName))
                    .findFirst().orElse(null);
        }
    }

    @Override
    public Resource load(String fileName) {
        String storageRoot = FILE_STORAGE_ROOT + File.separator + "img" + File.separator;
        try {
            Path path = Paths.get(storageRoot);
            Path path1 = findPath(path,fileName);
            if(path1 == null) return null;
            Resource resource = new UrlResource(path1.toUri());
            if(resource.exists() || resource.isReadable())
                return resource;
        } catch (IOException e) {
           e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean remove(String url) {
        String path = FILE_STORAGE_ROOT+ File.separator+"img"+ File.separator +url;
        File file = new File(path);
        if(file.exists() && file.isFile()){
            return file.delete();
        }
        return false;
    }

    @Override
    public void remove(Collection<String> urls) {
        if(!CollectionUtils.isEmpty(urls)){
            urls.stream().allMatch(this::remove);
        }
    }
}
