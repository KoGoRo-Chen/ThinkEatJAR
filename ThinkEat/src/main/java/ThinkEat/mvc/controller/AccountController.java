package ThinkEat.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Account")
//網頁路徑：http://localhost:8080/ThinkEat/mvc/Account
public class AccountController {

    @GetMapping("/Login")
    public String getLoginPage(){
        return "Account/Login";
    }

    @GetMapping("/AccessDenied")
    public String getAccessDeniedPage(){
        return "Account/AccessDenied";
    }

}
