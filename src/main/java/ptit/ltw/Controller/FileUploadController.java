package ptit.ltw.Controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ptit.ltw.Service.FirebaseImageService;

import java.io.IOException;

@Controller
@RequestMapping(value = "/file")
@AllArgsConstructor
public class FileUploadController {
    private final FirebaseImageService firebaseImageService;
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile[] files){
        StringBuilder fileUploaded = new StringBuilder();
        for (MultipartFile file : files) {
            try{
               fileUploaded.append(firebaseImageService.save(file).concat(", "));
            }catch (IOException e){
                e.getMessage();
            }
        }
        return ResponseEntity.ok().body(fileUploaded);
    }

    @GetMapping
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("path") String path) throws IOException {
        byte[] data = firebaseImageService.getFile(path);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-disposition", "attachment; filename=\"" + path + "\"")
                .body(byteArrayResource);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFile(@RequestParam("path") String path) throws IOException {
        firebaseImageService.delete(path);
        return ResponseEntity.ok().build();
    }

}
