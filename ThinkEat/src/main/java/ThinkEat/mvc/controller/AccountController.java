package ThinkEat.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Account")
//網頁路徑：http://localhost:8080/ThinkEat/mvc/Account
public class AccountController {

    //顯示會員中心頁面
    @GetMapping("/AccountCenter")
    public String getAccountCenterPage() {

        return "Account/AccountCenter";
    }

    //顯示註冊會員頁面
    @GetMapping("/SignIn")
    public String getSignInPage() {

        return "Account/SignIn";
    }

    //顯示更改密碼頁面
    @GetMapping("/ChangePassword")
    public String getPasswordPage() {

        return "Account/ChangePassword";
    }

    //顯示編輯會員頁面
    @GetMapping("/UpdateAccount")
    public String getUpdateAccountPage() {

        return "Account/UpdateAccount";
    }

}
