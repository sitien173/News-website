package ptit.ltw.Entity;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ptit.ltw.Model.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "AppUser",
        uniqueConstraints = {@UniqueConstraint(name = "appUser_UN_email",columnNames = "email")})
public class AppUser implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false,length = 100)
    private String firstName;

    @NotBlank
    @Column(nullable = false,length = 100)
    private String lastName;

    @Column(length = 100)
    private String avatar;

    @Email
    @NaturalId
    @Column(nullable = false,length = 100)
    private String email;

    @NotBlank
    @Column(nullable = false,length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private Role role = Role.USER;

    @Column(nullable = false)
    private Boolean isEnable = false;

    @Column(nullable = false)
    private Boolean isAccountNonLocked = true;

    @Column(updatable = false)
    private LocalDate createAt = LocalDate.now();

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "appUser")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
              org.hibernate.annotations.CascadeType.DELETE})
    private List<VerificationToken> verificationTokens = new ArrayList<>();

    @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY , orphanRemoval = true)
    @Cascade({org.hibernate.annotations.CascadeType.DELETE})
    private List<Post> posts;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY,
                orphanRemoval = true,
                cascade = CascadeType.ALL,
                mappedBy = "appUser")
    private List<Comment> comments;

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

    public void addVerificationToken(VerificationToken verificationToken){
        this.verificationTokens.add(verificationToken);
    }
}
