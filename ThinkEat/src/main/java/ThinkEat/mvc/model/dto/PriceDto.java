package ThinkEat.mvc.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "eatRepoList")
public class PriceDto {

    List<EatRepoDto> eatRepoList;
    private Integer id;
    private String name;
}
