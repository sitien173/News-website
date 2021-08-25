package ptit.ltw.Entity;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ptit.ltw.Enum.Sex;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "user",
        indexes = {@Index(name = "appUser_index_email",columnList = "email",unique = true),
                  @Index(name = "appUser_index_phone",columnList = "phone",unique = true)})
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            columnDefinition = "VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String firstName;

    @Column(nullable = false,
            columnDefinition = "VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String lastName;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String avatar;

    @Column(nullable = false)
    private Byte age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,
            columnDefinition = "VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private Sex sex;

    @Column(nullable = false,
            columnDefinition = "VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String address;

    @NaturalId
    @Column(nullable = false,
            columnDefinition = "VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String email;

    @Column(nullable = false,unique = true,
            columnDefinition = "VARCHAR(15) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String phone;

    @Column(nullable = false,
            columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,
            columnDefinition = "VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private Role role;

    @Column(nullable = false)
    private Boolean isEnable = false;

    @Column
    private Boolean isAccountNonLocked = true;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.REMOVE},mappedBy = "appUser")
    private Collection<VerificationToken> verificationTokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
      return authorities;
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
        return isAccountNonLocked;
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
