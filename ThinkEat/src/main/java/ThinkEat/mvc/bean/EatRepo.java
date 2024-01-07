package ThinkEat.mvc.bean;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//食記
public class EatRepo {
    //命名規則：屬性首字一定要小寫
    private User user;
    //文章ID
    private Integer eatRepoId;
    //文章標題
    private String eatRepoTitle;
    //餐廳資料
    private ResData resData;
    //用餐日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date eatDate;
    //價格
    private Integer priceId;
    private PriceData price;
    //標籤
    private Integer[] tagIds;
    private List<TagData> tags;
    //食記
    private String repo;
    //圖片
    private MultipartFile eatPic;
    private byte[] eatPicBytes;
    private String eatPicBase64;

    public Integer getResId() {
        ResData resData = getResData();

        if (resData == null) {
            return null;
        }

        return resData.getResId();
    }

    public String getResName() {
        ResData resData = getResData();

        if (resData == null) {
            return null;
        }

        return resData.getResName();
    }

    public String getResAddress() {
        ResData resData = getResData();

        if (resData == null) {
            return null;
        }

        return resData.getResAddress();
    }

    public String getPriceName() {
        PriceData price = getPrice();

        if (price == null) {
            return null;
        }

        return price.getName();
    }

    public String getAllTagNames() {
        if (tags != null && !tags.isEmpty()) {
            return tags.stream().map(TagData::getName).collect(Collectors.joining(", "));
        }
        return "";
    }
}


