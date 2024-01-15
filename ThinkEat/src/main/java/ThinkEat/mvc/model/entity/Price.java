package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "price")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "eatRepoList")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;

    @OneToMany(mappedBy = "price")
    private List<EatRepo> eatRepoList;
}
//@Column(nullable = false)