package ptit.ltw.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Post",uniqueConstraints = @UniqueConstraint(name = "UN_Post_slug",columnNames = "slug"))
@Data
@NoArgsConstructor
@NaturalIdCache
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId(mutable = true) // có thể thay đổi
    @Column(nullable = false)
    private String slug;

    @NotBlank(message = "Tile not empty")
    @Column(nullable = false)
    private String title;

    @Column
    private String banner;

    @Column
    private Integer view = 0;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "post",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_category",
            joinColumns = @JoinColumn(name = "p_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "c_id",referencedColumnName = "id"))
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<Category> categories = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "appuser_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_post_user"))
    private AppUser appUser;

    @Column(updatable = false)
    private LocalDate createAt = LocalDate.now();

    @NotBlank(message = "Content not empty")
    @Column(nullable = false)
    @Type(type = "text")
    private String content;

    @Column
    private Boolean isEnable = true;

    public void addCategory(Category category){
        this.categories.add(category);
    }

}
