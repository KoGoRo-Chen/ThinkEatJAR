package ThinkEat.mvc.model.dto;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FavList_EatRepoDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fl_er_ref_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "favlist_for_ref")
    private FavListDto favlist_for_ref;

    @ManyToOne
    @JoinColumn(name = "eatrepo_for_ref")
    private EatRepoDto eatRepo_for_ref;
}
