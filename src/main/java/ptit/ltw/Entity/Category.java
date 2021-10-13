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
    @Column(nullable = false,length = 50)
    private String name;

    @NaturalId
    @Column(nullable = false,length = 100)
    private String slug;

    @Column(length = 100)
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
