package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.Restaurant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class RestaurantPageDto {
    private List<Restaurant> restaurantList; // 存儲分頁查詢結果的列表
    private int currentPage; // 當前頁碼
    private int totalPage; // 總頁數

    public RestaurantPageDto(Page<Restaurant> restaurantPageDto) {
        this.restaurantList = restaurantPageDto.getContent();
        this.currentPage = restaurantPageDto.getNumber();
        this.totalPage = restaurantPageDto.getTotalPages();
    }
}
