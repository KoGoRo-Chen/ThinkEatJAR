package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpRequest;

@Controller
@RequestMapping("/Account")
//網頁路徑：http://localhost:8080/ThinkEat/mvc/Account
public class AccountController {

    //顯示會員中心頁面
    @GetMapping("/AccountCenter")
    public String getAccountCenterPage(HttpSession session,
                                       Model model) {
        User theUser = (User) session.getAttribute("user");
        if (theUser == null) {
            return "redirect:/ThinkEat/Index";
        }
        System.out.println(theUser);
        model.addAttribute("user", theUser);
        return "Account/AccountCenter";
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
