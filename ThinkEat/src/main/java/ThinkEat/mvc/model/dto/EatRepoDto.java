package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EatRepoDto {

    private Integer id;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date date;

    private String article;

    private UserDto eatRepo_User;

    private RestaurantDto restaurant;

    private PriceDto price;

    private List<FavListDto> favListList = new ArrayList<>();

    private List<TagDto> eatRepo_TagList = new ArrayList<>();

    private List<CommentDto> cmtList = new ArrayList<>();

    private List<PictureDto> picList = new ArrayList<>();

}
