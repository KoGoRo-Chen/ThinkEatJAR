package ThinkEat.mvc.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "eatrepo_tag")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EatRepo_Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "er_tag_ref_id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "eatRepoForRef")
    private EatRepo eatRepo_for_ref;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "tagForRef")
    private Tag tag_for_ref;

}
