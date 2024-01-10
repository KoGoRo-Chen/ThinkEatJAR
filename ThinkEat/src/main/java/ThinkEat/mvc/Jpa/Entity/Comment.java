package ThinkEat.mvc.Jpa.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "comment")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer id;

    @Column(nullable = false)
    private String cmtContext;

    @ManyToOne(targetEntity = EatRepo.class)
    @JoinColumn(name = "eatrepo_id")
    private EatRepo eatRepo;

}
