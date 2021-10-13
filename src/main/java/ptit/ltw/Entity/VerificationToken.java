package ptit.ltw.Entity;


import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "Verificationtoken",uniqueConstraints =
                @UniqueConstraint(name = "vf_UN_token",columnNames = "token"))
public class VerificationToken implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(nullable = false,length = 100)
    private String token;

    @Column(updatable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime expiredAt;
    @Column
    private LocalDateTime confirmedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "appuser_id",foreignKey = @ForeignKey(name = "FK_verificationToken_user"))
    private AppUser appUser;

    public VerificationToken(String token, LocalDateTime expiredAt, LocalDateTime confirmedAt, AppUser appUser) {
        this.token = token;
        this.expiredAt = expiredAt;
        this.confirmedAt = confirmedAt;
        this.appUser = appUser;
    }
}
