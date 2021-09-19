package ptit.ltw.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(columnDefinition = "VARCHAR(10000) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String banner;

    @Column(updatable = false)
    private LocalDate createAt = LocalDate.now();

    @Column
    private Boolean isEnable = true;

    @OneToMany(mappedBy = "category",orphanRemoval = true,fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.DELETE})
    private List<Post> posts;

}
