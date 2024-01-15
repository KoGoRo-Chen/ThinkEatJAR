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
    @Column(name = "fl_er_ref_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "favlist_for_ref")
    private FavList favlist_for_ref;

    @ManyToOne
    @JoinColumn(name = "eatrepo_for_ref")
    private EatRepo eatRepo_for_ref;
}
