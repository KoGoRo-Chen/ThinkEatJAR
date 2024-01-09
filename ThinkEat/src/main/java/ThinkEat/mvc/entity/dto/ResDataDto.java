package ThinkEat.mvc.entity.dto;

import ThinkEat.mvc.entity.EatRepo;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResDataDto {

    //欄位資料
    private Integer resDtoId;
    private String resDtoName;
    private String resDtoAddress;

    //關聯資料(食記)
    private EatRepoDto eatRepoDto;

}
