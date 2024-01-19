package ThinkEat.mvc.model.dto;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FavList_EatRepoDto {

    private Integer id;

    private FavListDto favlist_id;

    private EatRepoDto eatrepo_id;
}
