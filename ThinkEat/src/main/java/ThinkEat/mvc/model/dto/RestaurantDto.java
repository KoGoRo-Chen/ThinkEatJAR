package ThinkEat.mvc.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RestaurantDto {

    private Integer id;

    private String name;

    private String address;

    private List<EatRepoDto> eatRepoList;

}
