package ThinkEat.mvc.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ShowEatPageDto {
    private List<RestaurantDto> restaurantDtoList; // 存儲分頁查詢結果的列表
    private int currentPage; // 當前頁碼
    private int totalPage; // 總頁數

    public ShowEatPageDto(Page<RestaurantDto> showEatPageDtoPage) {
        this.restaurantDtoList = showEatPageDtoPage.getContent();
        this.currentPage = showEatPageDtoPage.getNumber();
        this.totalPage = showEatPageDtoPage.getTotalPages();
    }
}
