package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "picture")
@Data
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private Integer id;

    @Column(nullable = false)
    private String Path;

    @ManyToOne(targetEntity = EatRepo.class)
    @JoinColumn(name = "eatrepo_id")
    private EatRepo pic_EatRepo;


}
