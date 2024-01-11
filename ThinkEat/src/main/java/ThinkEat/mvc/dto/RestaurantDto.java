package ThinkEat.mvc.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RestaurantDto {

    //欄位資料
    private Integer id;
    private String name;
    private String address;

    //關聯資料(食記)
    private List<EatRepoDto> eatRepoDtoList;

}
