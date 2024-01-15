package ThinkEat.mvc.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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
