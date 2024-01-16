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

    private Integer listId;

    private String favListName;

    private UserDto favList_User;

    private Integer favListId;
    private List<EatRepoDto> favList_EatRepoList = new ArrayList<>();

}
