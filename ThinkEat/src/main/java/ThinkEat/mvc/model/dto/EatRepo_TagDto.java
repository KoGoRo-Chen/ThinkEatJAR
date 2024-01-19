package ThinkEat.mvc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EatRepo_TagDto {

    private Integer id;

    private EatRepoDto eatrepo_id;

    private TagDto tag_id;

}
