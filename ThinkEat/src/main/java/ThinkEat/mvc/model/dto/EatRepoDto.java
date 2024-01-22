package ThinkEat.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"article", "favListList", "cmtList", "picList", "eatRepo_TagList"})
public class EatRepoDto {

    private Integer id;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    private String article;

    private UserDto eatRepo_User;

    private RestaurantDto restaurant;

    private Integer priceId;
    private PriceDto price;

    private List<FavListDto> favListList = new ArrayList<>();

    private List<TagDto> eatRepo_TagList = new ArrayList<>();

    private List<CommentDto> cmtList = new ArrayList<>();

    private List<PictureDto> picList = new ArrayList<>();


}
