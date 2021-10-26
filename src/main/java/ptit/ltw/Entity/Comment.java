package ptit.ltw.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Comment")
@Data
@NoArgsConstructor
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_comment_post"))
    private Post post;

    @Column(updatable = false)
    private LocalDate createAt = LocalDate.now();

    @Column
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "appuser_id",foreignKey = @ForeignKey(name = "FK_comment_user"))
    private AppUser appUser;

}
