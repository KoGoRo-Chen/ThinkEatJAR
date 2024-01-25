package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.PictureDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.model.dto.UserDto;
import ThinkEat.mvc.model.entity.Restaurant;
import ThinkEat.mvc.model.entity.User;
import ThinkEat.mvc.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Controller
@RequestMapping("/")
public class ThinkEatController {

    private Logger logger = Logger.getLogger(getClass().getName());

    private final EatRepoService eatRepoService;
    private final RestaurantService restaurantService;
    private final PriceService priceService;
    private final TagService tagService;
    private final PictureService pictureService;
    private final UserService userService;

    @Autowired
    public ThinkEatController(EatRepoService eatRepoService,
                              RestaurantService restaurantService,
                              PriceService priceService,
                              TagService tagService,
                              PictureService pictureService,
                              UserService userService) {
        this.eatRepoService = eatRepoService;
        this.restaurantService = restaurantService;
        this.priceService = priceService;
        this.tagService = tagService;
        this.pictureService = pictureService;
        this.userService = userService;
    }


    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


    //顯示首頁
    @GetMapping("/Index")
    public String GetIndexPage(Model model) {

        //挑出所有餐廳
        List<Restaurant> restaurantList = restaurantService.getAllRestaurant();
        model.addAttribute("restaurantList", restaurantList);

        return "Index";
    }

    @GetMapping("/Login")
    public String getLoginPage() {

        return "Login";
    }

    @GetMapping("/AccessDenied")
    public String getAccessDeniedPage() {

        return "AccessDenied";
    }

    //顯示註冊會員頁面
    @GetMapping("/SignIn")
    public String getSignInPage(Model model) {
        User newUser = new User();
        model.addAttribute("user", newUser);
        return "SignIn";
    }

    //註冊會員
    @PostMapping("/submitRegistration")
    public String submitRegistration(@Valid @ModelAttribute("user") User user,
                                     BindingResult theBindingResult,
                                     HttpSession session,
                                     Model model) {

        String userName = user.getUsername();
        logger.info("Processing registration form for: " + userName);

        // 表單驗證
        if (theBindingResult.hasErrors()) {
            return "register/registration-form";
        }

        //檢查用戶是否存在
        User existingUser = userService.findUserByUsername(user.getUsername());
        if (existingUser != null) {
            model.addAttribute("registrationError", "這個帳號已經有人使用過了");
            logger.warning("這個帳號已經有人使用過了");
            return "SignIn";
        }

        Integer userId = userService.addUser(user);

        logger.info("Successfully created user: " + userName);
        session.setAttribute("user", user);

        return "Index";
    }
}


