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
    @JoinColumn(name = "favlist_id_ref")
    private Integer favList_id_ref;

    @ManyToOne
    @JoinColumn(name = "eatrepo_id_ref")
    private Integer eatRepo_id_ref;
}
