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

    //一篇文章可以有多張圖片
    @ManyToOne(targetEntity = EatRepo.class)
    @JoinColumn(name = "eatrepo_id")
    private EatRepo eatRepo;


}
