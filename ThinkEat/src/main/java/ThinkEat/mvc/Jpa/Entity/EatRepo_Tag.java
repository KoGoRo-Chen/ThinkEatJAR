package ThinkEat.mvc.Jpa.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "eatrepo_tag")
@Data
public class EatRepo_Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "er_tag_ref_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "eatrepo_id_ref")
    private Integer eatRepo_id_ref;

    @ManyToOne
    @JoinColumn(name = "tag_id_ref")
    private Integer tag_id_ref;

}
