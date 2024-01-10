package ThinkEat.mvc.OldBean.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EatRepoDto {
    //餐廳資料
    private ResDataDto resDataDto;
    //文章ID
    private Integer eatRepoDtoId;
    //文章標題
    private String eatRepoDtoTitle;
    //用餐日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date eatDateDto;


    //價格
    private Integer priceDtoId;
    private PriceDataDto priceDataDto;
    //標籤
    private Integer[] tagDtoIds;
    private List<TagDataDto> tagDataDtos;
    //食記
    private String repo;

    public String getPriceName() {
        PriceDataDto priceDto = getPriceDataDto();

        if (priceDto == null) {
            return null;
        }

        return priceDto.getName();
    }

    public String getAllTagNames() {
        if (tagDataDtos != null && !tagDataDtos.isEmpty()) {
            return tagDataDtos.stream().map(TagDataDto::getName).collect(Collectors.joining(", "));
        }
        return "";
    }

}