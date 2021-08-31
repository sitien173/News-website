package ptit.ltw.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "Category",uniqueConstraints = {
        @UniqueConstraint(name = "category_UN_slug",columnNames = "slug"),
        @UniqueConstraint(name = "category_UN_name", columnNames = "name")
})
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,
            columnDefinition = "VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String name;
    @NaturalId
    @Column(nullable = false,
            columnDefinition = "VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String slug;

    @Column(nullable = false,
            columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String banner;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createAt;

    @JsonIgnore
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Post> posts;

}
