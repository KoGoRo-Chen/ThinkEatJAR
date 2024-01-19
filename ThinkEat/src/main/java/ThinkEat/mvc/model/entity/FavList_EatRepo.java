package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "favlist_eatrepo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavList_EatRepo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "favlist_id")
    private FavList favlist_id;

    @ManyToOne
    @JoinColumn(name = "eatrepo_id")
    private EatRepo eatrepo_id;
}
