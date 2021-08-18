package ptit.ltw.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "UN_username",columnNames = "username"),
                            @UniqueConstraint(name = "UN_email", columnNames = "email"),
                            @UniqueConstraint(name = "UN_phone", columnNames = "phone")})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            columnDefinition = "VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String name;

    @Column(nullable = false,
            columnDefinition = "VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String address;

    @Column(nullable = false,
            columnDefinition = "VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String email;

    @Column(nullable = false,
            columnDefinition = "VARCHAR(15) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String phone;

    @Column(nullable = false,
            columnDefinition = "VARCHAR(30) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false,
            columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,
            columnDefinition = "VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private Role role;

    @Column(nullable = false)
    private Boolean isEnable = false;

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
        return username;
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
