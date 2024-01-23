package ThinkEat.mvc.model.dto;

import jakarta.persistence.Column;
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

    private String name;

    private String description;

    private List<UserDto> userList = new ArrayList<>();
}
