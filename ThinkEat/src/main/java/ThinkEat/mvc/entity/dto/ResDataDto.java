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
    private Integer resId;
    private String resName;
    private String resAddress;

    //關聯資料(食記)
    private EatRepoDto eatRepoDto;

    public ResDataDto(Integer resId, String resName, String resAddress) {
        this.resId = resId;
        this.resName = resName;
        this.resAddress = resAddress;
    }

}
