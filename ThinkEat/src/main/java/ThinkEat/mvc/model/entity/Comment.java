package ThinkEat.mvc.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(columnDefinition = "timestamp NOT NULL")
    private Date date;

    @Column
    private String commentContext;

    @ManyToOne(targetEntity = EatRepo.class)
    @JoinColumn(name = "eatrepo_id")
    private EatRepo comment_EatRepo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User comment_User;

}

//@Column(nullable = false)j
