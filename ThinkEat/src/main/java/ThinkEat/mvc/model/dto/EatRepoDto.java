package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.*;
import lombok.*;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EatRepoDto {

    private Integer id;

    private String title;

    private Date date;

    private String article;

    private UserDto eatRepo_User;

    private RestaurantDto restaurant;

    private PriceDto price;

    private Set<FavListDto> favListList = new LinkedHashSet<>();

    private Set<TagDto> eatRepo_TagList = new LinkedHashSet<>();

    private Set<CommentDto> cmtList = new LinkedHashSet<>();

    private Set<PictureDto> picList = new LinkedHashSet<>();

}
