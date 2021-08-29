package ptit.ltw.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Collection;


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

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Collection<Post> posts;

}
