package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.UserDto;
import ThinkEat.mvc.model.entity.User;
import ThinkEat.mvc.model.entity.UserDetails;
import ThinkEat.mvc.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpRequest;

@Controller
@RequestMapping("/Account")
//網頁路徑：http://localhost:8080/ThinkEat/mvc/Account
public class AccountController {

    private final UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    //顯示會員中心頁面
    @GetMapping("/AccountCenter")
    public String getAccountCenterPage(Authentication authentication,
                                       Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        UserDto existingUser = userService.findUserByUsername(username);
        if (existingUser == null) {
            return "redirect:/ThinkEat/Login";
        } else {
            model.addAttribute("user", existingUser);
            return "Account/AccountCenter";
        }
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
