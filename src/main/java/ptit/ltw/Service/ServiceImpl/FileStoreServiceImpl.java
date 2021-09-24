package ptit.ltw.Service.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ptit.ltw.Service.IService.FileStoreService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class FileStoreServiceImpl implements FileStoreService {
    public static String FILE_STORAGE_ROOT = getRootPath();
    private static String getRootPath(){
      /*  try {
           // Resource resource = new ClassPathResource("/static/assets");
           // return resource.getFile().getAbsolutePath();
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
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }

    private boolean isAllow(String fileName){
        String[] allowFiles = {".gif",".png",".jpg",".jpeg",".bpm",".svg"};
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        List<String> suffixList = Arrays.stream(allowFiles).collect(Collectors.toList());
        return suffixList.contains(suffix);
    }

    @Override
    public String upload(MultipartFile multipartFile) throws FileNotFoundException {
        if(!Strings.isNotBlank(multipartFile.getOriginalFilename())
                || !isAllow(multipartFile.getOriginalFilename())){
            throw new FileNotFoundException("File not accept");
        }
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

    @Override
    public Resource load(String fileName) {
        try {
            try {
                String storageRoot = FILE_STORAGE_ROOT + File.separator + "img" + File.separator;
                Path file = Paths.get(storageRoot).resolve(fileName);
                Resource resource = new UrlResource(file.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                }
            }catch (InvalidPathException e){
                URL url = new URL(fileName);
                InputStreamResource resource = new InputStreamResource(url.openStream());
                return resource;
            }
        } catch (IOException e ) {
            // true logic
        }
        return null;
    }

    @Override
    public boolean remove(String url) {
        String path = FILE_STORAGE_ROOT+ File.separator+"img"+url;
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
