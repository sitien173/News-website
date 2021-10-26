package ptit.ltw.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;

@Entity
@Table(name = "Subscriber")
@NaturalIdCache
@Data
@NoArgsConstructor
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,updatable = false)
    @NaturalId
    private String email;

    public Subscriber(String email) {
        this.email = email;
    }
}
