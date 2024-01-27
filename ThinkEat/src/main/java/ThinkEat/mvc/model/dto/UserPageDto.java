package ThinkEat.mvc.model.dto;

import ThinkEat.mvc.model.entity.Restaurant;
import ThinkEat.mvc.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class UserPageDto {
    private List<User> userList; // 存儲分頁查詢結果的列表
    private int currentPage; // 當前頁碼
    private int totalPage; // 總頁數

    public UserPageDto(Page<User> userPage) {
        this.userList = userPage.getContent();
        this.currentPage = userPage.getNumber();
        this.totalPage = userPage.getTotalPages();
    }
}
