package ThinkEat.mvc.model.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"comment_EatRepo", "comment_User"})
public class CommentDto {

    private Integer id;

    private String commentContext;

    private Date date;

    private EatRepoDto comment_EatRepo;

    private UserDto comment_User;

}
