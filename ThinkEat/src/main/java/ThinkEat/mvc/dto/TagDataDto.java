package ThinkEat.mvc.dto;

import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TagDataDto {
    private Integer id;
    private String tagName;
    private Set<EatRepoDto> tag_EatRepoList = new LinkedHashSet<>();

}
