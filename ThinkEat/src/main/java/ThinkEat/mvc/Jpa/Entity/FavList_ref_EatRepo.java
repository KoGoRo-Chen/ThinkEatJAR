package ThinkEat.mvc.Jpa.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "favlist_eatrepo")
@Data
public class FavList_ref_EatRepo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "favList_id")
    private FavList favList;

    @ManyToOne
    @JoinColumn(name = "eatRepo_id")
    private EatRepo eatRepo;
}
