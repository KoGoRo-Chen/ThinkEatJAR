package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.EatRepo;
import ThinkEat.mvc.model.entity.Restaurant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class EatRepoPageDto {
    private List<EatRepo> eatRepoList; // 存儲分頁查詢結果的列表
    private int currentPage; // 當前頁碼
    private int totalPage; // 總頁數

    public EatRepoPageDto(Page<EatRepo> eatRepoPageDto) {
        this.eatRepoList = eatRepoPageDto.getContent();
        this.currentPage = eatRepoPageDto.getNumber();
        this.totalPage = eatRepoPageDto.getTotalPages();
    }
}
