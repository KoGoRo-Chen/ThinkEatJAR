package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.Picture;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"eatRepoList", "resPicList"})
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    private Integer id;

    private String name;

    private String address;

    private List<EatRepoDto> eatRepoList;

    private List<PictureDto> resPicList;

}
