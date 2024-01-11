package ThinkEat.mvc.model.dto;

import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TagDto {

    private Integer id;

    private String name;

    private Set<EatRepoDto> tag_EatRepoList = new LinkedHashSet<>();

}
