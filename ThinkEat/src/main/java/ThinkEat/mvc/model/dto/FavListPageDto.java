package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.FavList;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class FavListPageDto {
    private List<FavList> favList; // 存儲分頁查詢結果的列表
    private int currentPage; // 當前頁碼
    private int totalPage; // 總頁數

    public FavListPageDto(Page<FavList> favListPage) {
        this.favList = favListPage.getContent();
        this.currentPage = favListPage.getNumber();
        this.totalPage = favListPage.getTotalPages();
    }
}
