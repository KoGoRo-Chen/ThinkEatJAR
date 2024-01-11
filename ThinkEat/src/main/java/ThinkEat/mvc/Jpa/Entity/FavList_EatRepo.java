package ThinkEat.mvc.Jpa.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "favlist_eatrepo")
@Data
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
