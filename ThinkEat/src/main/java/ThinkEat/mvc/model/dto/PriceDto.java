package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.EatRepo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "eatRepoList")
public class PriceDto {

    private Integer id;

    private String name;

    List<EatRepoDto> eatRepoList;
}
