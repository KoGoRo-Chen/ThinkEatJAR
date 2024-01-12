package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"comment_EatRepo", "comment_User"})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer id;

    @Column
    private String commentContext;

    @ManyToOne
    @JoinColumn(name = "eatrepo_id")
    private EatRepo comment_EatRepo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User comment_User;

}

//@Column(nullable = false)j
