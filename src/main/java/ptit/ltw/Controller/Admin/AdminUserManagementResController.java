package ptit.ltw.Controller.Admin;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.api.client.json.Json;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptit.ltw.Entity.User;
import ptit.ltw.Service.UserService;

@RestController
@RequestMapping("/api/admin/user")
@AllArgsConstructor
public class AdminUserManagementResController {
    private final UserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(userService.findById(id));
    }
    @GetMapping("/list")
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.ok().body(userService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

}
