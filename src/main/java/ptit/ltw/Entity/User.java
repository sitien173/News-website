package ptit.ltw.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ptit.ltw.Enum.Role;
import ptit.ltw.Enum.Sex;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "UN_email", columnNames = "email"),
                            @UniqueConstraint(name = "UN_phone", columnNames = "phone")})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotEmpty(message = "First Name is not empty")
    @Size(min = 2, max = 50, message = "Minimum 2 characters and maximum 50 characters")
    @Column(nullable = false,
            columnDefinition = "VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String firstName;

    @NotEmpty(message = "Last Name is not empty")
    @Size(min = 2, max = 50, message = "Minimum 2 characters and maximum 50 characters")
    @Column(nullable = false,
            columnDefinition = "VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String lastName;


    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String avatar;

    @Min(value = 18, message = "Minimum is 18")
    @Max(value = 100, message = "Maximum is 100")
    @Column(nullable = false)
    private Byte age = 18; // default 18

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,
            columnDefinition = "VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private Sex sex = Sex.MALE; // default MALE


    @Column(nullable = false,
            columnDefinition = "VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String address;

    @Email(message = "Email Malformed ex: emxample123@gmail.com")
    @Column(nullable = false,
            columnDefinition = "VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String email;

    @Pattern(regexp = "^(09|03|07|08|05)+([0-9]{8})$",
            message = "Phone Malformed ex: 0342884211")
    @Size(min = 10, max = 12, message = "Minimum 10 characters and maximum 12 characters")
    @Column(nullable = false,
            columnDefinition = "VARCHAR(15) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String phone;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,100}$",
            message = "Password up to 8 characters. It includes lowercase, uppercase, characters and numbers")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false,
            columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,
            columnDefinition = "VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private Role role = Role.USER; // default role is User

    @Column(nullable = false)
    private Boolean isEnable = false;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "user")
    private Collection<VerificationToken> verificationTokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(getRole().name()));
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }
}
