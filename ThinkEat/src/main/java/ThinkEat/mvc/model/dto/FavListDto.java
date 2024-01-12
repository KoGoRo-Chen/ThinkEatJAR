package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.EatRepo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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

    private List<EatRepoDto> favList_EatRepoList = new ArrayList<>();

}
