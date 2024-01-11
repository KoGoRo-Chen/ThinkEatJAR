package ThinkEat.mvc.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentDto {

    private Integer id;

    private String commentContext;

    private EatRepoDto comment_EatRepo;

    private UserDto comment_User;

}
