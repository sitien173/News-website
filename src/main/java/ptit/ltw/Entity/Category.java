package ptit.ltw.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "Category",uniqueConstraints = {
        @UniqueConstraint(name = "category_UN_slug",columnNames = "slug"),
        @UniqueConstraint(name = "category_UN_name", columnNames = "name")
})
@NoArgsConstructor
@Getter
@Setter
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
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

    @Column(columnDefinition = "VARCHAR(1000) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String banner;

    @Column(updatable = false)
    private LocalDate createAt = LocalDate.now();

    @Column
    private Boolean isEnable = true;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "categories")
    private List<Post> posts;

}
