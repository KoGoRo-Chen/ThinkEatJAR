package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.EatRepo;
import ThinkEat.mvc.model.entity.Tag;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EatRepo_TagDto {

    private Integer id;

    private EatRepoDto eatRepo_for_ref;

    private TagDto tag_for_ref;

}
