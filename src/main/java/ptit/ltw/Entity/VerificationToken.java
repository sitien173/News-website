package ptit.ltw.Entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "verificationToken_token_unique", columnNames = "token"))
public class VerificationToken  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;
    @Column
    private LocalDateTime confirmedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_verificationToken_user"))
    private AppUser appUser;

    public VerificationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, LocalDateTime confirmedAt, AppUser appUser) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.confirmedAt = confirmedAt;
        this.appUser = appUser;
    }
}
