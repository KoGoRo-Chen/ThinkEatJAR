package ThinkEat.mvc.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"favList_User", "favList_EatRepoList"})
public class FavListDto {

    private Integer id;

    private String favListName;

    private UserDto favList_User;

    private List<EatRepoDto> favList_EatRepoList = new ArrayList<>();

}
