package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "comment")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer id;

    @Column
    private String commentContext;

    @ManyToOne
    @JoinColumn(name = "eatrepo_id")
    EatRepo comment_EatRepo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User comment_User;

}

//@Column(nullable = false)j
