package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.entity.EatRepo;
import ThinkEat.mvc.model.entity.User;
import ThinkEat.mvc.model.entity.UserDetails;
import ThinkEat.mvc.service.EatRepoService;
import ThinkEat.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/BackEnd")
//網頁路徑：http://localhost:8080/ThinkEat/mvc/Account
public class BackendController {

    private final UserService userService;
    private final EatRepoService eatRepoService;

    @Autowired
    public BackendController(UserService userService,
                             EatRepoService eatRepoService) {
        this.userService = userService;
        this.eatRepoService = eatRepoService;
    }

    //顯示會員管理
    @GetMapping("/")
    public String getAccountCenterPageForDealing(Authentication authentication,
                                                 RedirectAttributes redirectAttributes,
                                                 Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User existingUser = userService.findUserByUsername(username);
        if (existingUser == null) {
            redirectAttributes.addFlashAttribute("NotLoginErrorMessage", "請登入後再發表留言。");
            return "redirect:/ThinkEat/Login";
        }
        return "BackEnd/BackEnd";
    }


}
