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

    private EatRepoDto eatRepo_for_ref;

    private TagDto tag_for_ref;

}
