package ThinkEat.mvc.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"authorityList"})
public class AccessDto {

    private Integer id;

    private String accessName;

    private String accessFunction;

    private List<AuthorityDto> authorityList = new ArrayList<>();

}
