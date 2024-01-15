package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.EatRepo;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"eatRepoList", "commentList", "favLists"})
public class UserDto {

    private Integer id;

    private String nickName;

    private String password;

    private String userName;

    private List<EatRepo> eatRepoList = new ArrayList<>();

    private List<CommentDto> commentList = new ArrayList<>();

    private AuthorityDto authority;

    private List<FavListDto> favLists = new ArrayList<>();
}
