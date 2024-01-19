package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "picture")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"pic_EatRepo", "pic_Restaurant"})
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column
    private String filename;

    @Column
    private String filePath;

    @Column
    private String htmlPath;

    @ManyToOne(targetEntity = EatRepo.class, cascade = CascadeType.DETACH)
    @JoinColumn(name = "eatrepo_id")
    private EatRepo pic_EatRepo;

    @ManyToOne(targetEntity = Restaurant.class, cascade = CascadeType.DETACH)
    @JoinColumn(name = "restaurant_id")
    private Restaurant pic_Restaurant;

}
//@Column(nullable = false)