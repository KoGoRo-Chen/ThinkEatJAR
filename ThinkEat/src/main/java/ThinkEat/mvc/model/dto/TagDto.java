package ThinkEat.mvc.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"tag_EatRepoList"})
public class TagDto {

    private Integer id;

    private String name;

    private List<EatRepoDto> tag_EatRepoList = new ArrayList<>();

    public TagDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
