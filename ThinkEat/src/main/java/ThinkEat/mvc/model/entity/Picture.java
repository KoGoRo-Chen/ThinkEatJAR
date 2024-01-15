package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "picture")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"pic_EatRepo"})
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private Integer id;

    @Column
    private String filename;

    @Column
    private String Path;

    @ManyToOne(targetEntity = EatRepo.class)
    @JoinColumn(name = "eatrepo_id")
    private EatRepo pic_EatRepo;


}
//@Column(nullable = false)