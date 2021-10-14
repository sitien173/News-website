package ptit.ltw.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Post")
@Data
@NoArgsConstructor
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String title;

    @Column(columnDefinition = "VARCHAR(10000) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String banner;

    @Column
        @ColumnDefault("0")
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

    @Column(nullable = false)
    @Type(type = "text")
    private String content;

    @Column
    private Boolean isEnable = true;

    public void addCategory(Category category){
        this.categories.add(category);
    }

}
