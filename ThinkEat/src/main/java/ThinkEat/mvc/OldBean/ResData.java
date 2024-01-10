package ThinkEat.mvc.OldBean;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResData {
    //餐廳Id
    private Integer resId;
    //餐廳名稱
    private String resName;
    //餐廳地址
    private String resAddress;

    //關聯資料(食記)
    private EatRepo eatRepo;

    public ResData(Integer resId, String resName, String resAddress) {
        this.resId = resId;
        this.resName = resName;
        this.resAddress = resAddress;
    }
}
