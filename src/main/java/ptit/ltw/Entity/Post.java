package ptit.ltw.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Post")
@Getter
@Setter
@NoArgsConstructor
public class Post {
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "category_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_post_category"))
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name = "appuser_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_post_user"))
    private AppUser appUser;

    @Column(updatable = false)
    private LocalDate createAt = LocalDate.now();

    @Column(nullable = false,
            columnDefinition = "TEXT(65535) CHARACTER SET utf8 COLLATE utf8_general_ci")
    @Type(type = "text")
    private String content;

    @Column
    private Boolean isEnable = true;

}
