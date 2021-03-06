package ptit.ltw.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "Category",uniqueConstraints = {
        @UniqueConstraint(name = "category_UN_slug",columnNames = "slug"),
        @UniqueConstraint(name = "category_UN_name", columnNames = "name")
})
@Data
@NoArgsConstructor
@NaturalIdCache
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name not empty")
    @Column(nullable = false,length = 50)
    private String name;

    @NaturalId(mutable = true)
    @Column(nullable = false,length = 100)
    private String slug;

    @Column(length = 100)
    private String banner;

    @Column(updatable = false)
    private LocalDate createAt = LocalDate.now();

    @Column
    private Boolean isEnable = true;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "categories")
    private List<Post> posts;

}
