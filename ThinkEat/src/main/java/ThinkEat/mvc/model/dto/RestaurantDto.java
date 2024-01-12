package ThinkEat.mvc.model.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class RestaurantDto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String address;

    private List<EatRepoDto> eatRepoList;

}
