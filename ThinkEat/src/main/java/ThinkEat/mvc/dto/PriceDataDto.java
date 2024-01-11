package ThinkEat.mvc.dto;

import ThinkEat.mvc.Jpa.Entity.EatRepo;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PriceDataDto {
    protected Integer id;
    protected String name;
    private EatRepoDto eatRepoDto;
}
