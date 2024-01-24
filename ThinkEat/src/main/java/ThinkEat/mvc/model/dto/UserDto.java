package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.Authority;
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

    private String username;

    private String password;

    private String nickname;

    private Integer favListCount;

    private boolean enabled;

    private boolean tokenExpired;

    private List<EatRepo> eatRepoList = new ArrayList<>();

    private List<CommentDto> commentList = new ArrayList<>();

    private List<AuthorityDto> authorities = new ArrayList<>();

    private List<FavListDto> favLists = new ArrayList<>();
}
