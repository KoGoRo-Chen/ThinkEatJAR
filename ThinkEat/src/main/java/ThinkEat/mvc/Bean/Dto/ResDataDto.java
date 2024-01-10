package ThinkEat.mvc.Bean.Dto;

import ThinkEat.mvc.entity.EatRepo;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResDataDto {

    //欄位資料
    private Integer resId;
    private String resName;
    private String resAddress;

    //關聯資料(食記)
    private EatRepoDto eatRepoDto;

}
