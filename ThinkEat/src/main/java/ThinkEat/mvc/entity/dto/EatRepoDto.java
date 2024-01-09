package ThinkEat.mvc.entity.dto;

import ThinkEat.mvc.entity.PriceData;
import ThinkEat.mvc.entity.ResData;
import ThinkEat.mvc.entity.TagData;
import ThinkEat.mvc.entity.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EatRepoDto {
    //餐廳資料
    private ResDataDto resDataDto;
    //使用者
    private User user;
    //文章ID
    private Integer eatRepoId;
    //文章標題
    private String eatRepoTitle;
    //用餐日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date eatDate;


    //價格
    private Integer priceId;
    private PriceDataDto priceDataDto;
    //標籤
    private Integer[] tagIds;
    private List<TagDataDto> tagDataDtos;
    //食記
    private String repo;

}
