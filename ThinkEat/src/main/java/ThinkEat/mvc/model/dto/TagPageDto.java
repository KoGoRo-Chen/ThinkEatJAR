package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.Price;
import ThinkEat.mvc.model.entity.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class TagPageDto {
    private List<Tag> tagList; // 存儲分頁查詢結果的列表
    private int currentPage; // 當前頁碼
    private int totalPage; // 總頁數

    public TagPageDto(Page<Tag> tagPage) {
        this.tagList = tagPage.getContent();
        this.currentPage = tagPage.getNumber();
        this.totalPage = tagPage.getTotalPages();
    }
}
