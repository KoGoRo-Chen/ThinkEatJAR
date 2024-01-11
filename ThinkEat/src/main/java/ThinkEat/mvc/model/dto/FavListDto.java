package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.EatRepo;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FavListDto {

    private Integer id;

    private String favListName;

    private UserDto favList_User;

    private Set<EatRepoDto> favList_EatRepoList = new LinkedHashSet<>();

}
