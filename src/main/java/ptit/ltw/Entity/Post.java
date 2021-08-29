package ptit.ltw.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "Post",uniqueConstraints =
                    @UniqueConstraint(name = "Post_UN_slug",columnNames = "slug"))
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            columnDefinition = "VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci")
    private String title;

    @Column(nullable = false,
            columnDefinition = "VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String slug;

    @Column(nullable = false,
            columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci")
    private String banner;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE,mappedBy = "post")
    private Collection<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",foreignKey = @ForeignKey(name = "FK_post_category"))
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appuser_id",foreignKey = @ForeignKey(name = "FK_post_user"))
    private AppUser appUser;

    @Column(nullable = false)
    private LocalDateTime creatAt;

    @Column(nullable = false,
            columnDefinition = "TEXT(65535) CHARACTER SET utf8 COLLATE utf8_general_ci")
    @Type(type = "text")
    private String content;

}
