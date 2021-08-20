package ptit.ltw.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptit.ltw.Service.UserService;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserResController {
    private final UserService userService;

    @PostMapping("/phone")
    public ResponseEntity<?> checkPhone(@RequestParam("phone") String phone){
        if(userService.findByPhone(phone) != null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }
    @PostMapping("/email")
    public ResponseEntity<?> checkEmail(@RequestParam("email") String email){
        if(userService.findByEmail(email) != null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

}
