package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.Price;
import ThinkEat.mvc.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PricePageDto {
    private List<Price> priceList; // 存儲分頁查詢結果的列表
    private int currentPage; // 當前頁碼
    private int totalPage; // 總頁數

    public PricePageDto(Page<Price> pricePage) {
        this.priceList = pricePage.getContent();
        this.currentPage = pricePage.getNumber();
        this.totalPage = pricePage.getTotalPages();
    }
}
