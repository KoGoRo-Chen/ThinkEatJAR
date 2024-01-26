package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.UserDto;
import ThinkEat.mvc.model.entity.EatRepo;
import ThinkEat.mvc.model.entity.User;
import ThinkEat.mvc.model.entity.UserDetails;
import ThinkEat.mvc.service.EatRepoService;
import ThinkEat.mvc.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpRequest;
import java.util.List;

@Controller
@RequestMapping("/Account")
//網頁路徑：http://localhost:8080/ThinkEat/mvc/Account
public class AccountController {

    private final UserService userService;
    private final EatRepoService eatRepoService;

    @Autowired
    public AccountController(UserService userService,
                             EatRepoService eatRepoService) {
        this.userService = userService;
        this.eatRepoService = eatRepoService;
    }

    //顯示會員中心頁面
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
        redirectAttributes.addAttribute("userId", existingUser.getId());
        return "redirect:/ThinkEat/Account/{userId}";
    }

    //顯示會員中心頁面(會員)
    @GetMapping("/{userId}")
    public String getAccountCenterPage(@PathVariable("userId") Integer userId,
                                       Authentication authentication,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User existingUser = userService.findUserByUsername(username);
        if (existingUser.getId() == userId) {
            model.addAttribute("user", userService.getUserById(userId));
            List<EatRepo> eatRepoList = existingUser.getEatRepoList();
            if (eatRepoList != null) {
                model.addAttribute("eatRepoList", eatRepoList);
                System.out.println(eatRepoList);
                return "Account/AccountCenter";
            } else {
            }

        }
        redirectAttributes.addFlashAttribute("NotLoginErrorMessage", "請登入後再發表留言。");
        return "redirect:/ThinkEat/Login";
    }

    //更改會員暱稱
    @PostMapping("/ChangeUserNickname")
    public String changeUserNickname(@RequestParam("userId") Integer userId,
                                     @RequestParam("name") String name,
                                     RedirectAttributes redirectAttributes) {
        User user = userService.getUserById(userId);
        userService.updateUserNickNameById(userId, name);
        redirectAttributes.addAttribute("userId", userId);
        return "redirect:/ThinkEat/Account/{userId}";
    }

    //更改會員密碼
    @PostMapping("/ChangeUserPassword")
    public String changeUserPassword(@RequestParam("userId") Integer userId,
                                     @RequestParam("oldPassword") String oldPassword,
                                     @RequestParam("newPassword") String newPassword,
                                     RedirectAttributes redirectAttributes) {
        User user = userService.getUserById(userId);
        userService.updateUserPasswordById(userId, oldPassword, newPassword);

        redirectAttributes.addAttribute("userId", userId);
        return "redirect:/ThinkEat/Account/{userId}";
    }

    //在會員中心刪除文章
    @PostMapping("/DeleteEatRepoFromBackend")
    public String DeleteEatRepoFromBackend(@RequestParam("eatRepoId") Integer eatRepoId,
                                           @RequestParam("userId") Integer userId,
                                           RedirectAttributes redirectAttributes) {
        eatRepoService.delete(eatRepoId);

        redirectAttributes.addAttribute("userId", userId);

        return "redirect:/ThinkEat/Account/{userId}";

    }

    //進入管理後台
    @GetMapping("/Backend")
    public String getBackendPage() {
        return "Account/Backend";
    }

}
