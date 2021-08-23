package ptit.ltw.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ptit.ltw.Enum.Role;
import ptit.ltw.Enum.Sex;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private static final long serialVersionUID = 5186013952828648626L;
    private Long appUserId;
    @NotEmpty(message = "First Name is not empty")
    @Size(min = 2, max = 50, message = "Minimum 2 characters and maximum 50 characters")
    private String firstName;

    @NotEmpty(message = "Last Name is not empty")
    @Size(min = 2, max = 50, message = "Minimum 2 characters and maximum 50 characters")
    private String lastName;

    private String avatar;

    @NotEmpty(message = "Address is not empty")
    @Size(min = 10, max = 100, message = "Minimum 2 characters and maximum 50 characters")
    private String address;

    @Min(value = 18, message = "Minimum is 18")
    @Max(value = 100, message = "Maximum is 100")
    private Byte age = 18;

    private Sex sex = Sex.MALE;

    @Email(message = "Email Malformed ex: emxample123@gmail.com")
    private String email;

    @Pattern(regexp = "^(09|03|07|08|05)+([0-9]{8})$",
            message = "Phone Malformed ex: 0342884211")
    @Size(min = 10, max = 12, message = "Minimum 10 characters and maximum 12 characters")
    private String phone;
    private Role role = Role.USER;
}
