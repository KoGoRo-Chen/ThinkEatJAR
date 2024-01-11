package ThinkEat.mvc.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PriceDto {

    protected Integer id;

    protected String name;

    private EatRepoDto eatRepoDto;
}
