package ptit.ltw.Dto;

import lombok.Data;
import ptit.ltw.Enum.Role;
import ptit.ltw.Enum.Sex;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String avatar;
    private Integer age;
    private Sex sex = Sex.MALE;
    private Role role = Role.USER;
    private String email;
    private String phone;
    private Boolean isEnable = false;
    private Boolean isAccountNonLocked = true;
}
