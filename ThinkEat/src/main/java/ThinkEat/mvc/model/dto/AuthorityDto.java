package ThinkEat.mvc.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"userList"})
public class AuthorityDto {

    private Integer id;

    private String authorityName;

    private List<UserDto> userList = new ArrayList<>();

    private List<AccessDto> accessList = new ArrayList<>();


}
