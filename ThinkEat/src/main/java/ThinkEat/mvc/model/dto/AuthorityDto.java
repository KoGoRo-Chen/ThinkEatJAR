package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.Access;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthorityDto {

    private Integer id;

    private String authorityName;

    private List<UserDto> userList = new ArrayList<>();

    private List<AccessDto> accessList = new ArrayList<>();


}
