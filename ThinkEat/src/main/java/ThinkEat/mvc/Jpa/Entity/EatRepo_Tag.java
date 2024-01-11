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
    @JoinColumn(name = "eatrepo_for_ref")
    private EatRepo eatRepo_for_ref;

    @ManyToOne
    @JoinColumn(name = "tag_for_ref")
    private Tag tag_for_ref;

}
