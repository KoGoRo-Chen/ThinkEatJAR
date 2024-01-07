package ThinkEat.mvc.bean;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    //用戶ID
    private Integer userId;
    //用戶名稱
    private String userNickName;
    //帳號
    private String username;
    //密碼
    private String password;


}
