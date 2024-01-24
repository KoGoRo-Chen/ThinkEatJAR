package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.Restaurant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ShowEatPageDto {
    private List<Restaurant> restaurantList; // 存儲分頁查詢結果的列表
    private int currentPage; // 當前頁碼
    private int totalPage; // 總頁數

    public ShowEatPageDto(Page<Restaurant> showEatPagePage) {
        this.restaurantList = showEatPagePage.getContent();
        this.currentPage = showEatPagePage.getNumber();
        this.totalPage = showEatPagePage.getTotalPages();
    }
}
