package ThinkEat.mvc.Jpa.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "picture")
@Data
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private Integer id;

    @Column(nullable = false)
    private String picPath;

    @ManyToOne(targetEntity = EatRepo.class)
    @JoinColumn(name = "eatrepo_id")
    private EatRepo eatRepo;


}
