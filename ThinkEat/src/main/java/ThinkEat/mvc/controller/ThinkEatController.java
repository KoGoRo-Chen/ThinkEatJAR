package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.PictureDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.model.dto.UserDto;
import ThinkEat.mvc.model.entity.User;
import ThinkEat.mvc.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/")
public class ThinkEatController {
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
        List<RestaurantDto> restaurantDtoList = restaurantService.getAllRestaurant();
        model.addAttribute("restaurantDtoList", restaurantDtoList);

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
        UserDto newUserDto = new UserDto();
        model.addAttribute("userDto", newUserDto);
        return "SignIn";
    }

    //註冊會員
    @PostMapping("/submitRegistration")
    public String submitRegistration(@ModelAttribute("userDto") UserDto userDto,
                                     HttpSession session,
                                     Model model) {
        UserDto existingUser = userService.findUserByUsername(userDto.getUsername());
        if (existingUser != null) {
            model.addAttribute("registrationError", "User already exists.");
            return "redirect:/ThinkEat/LogIn";
        }

        Integer userId = userService.addUser(userDto);
        userDto.setId(userId);
        session.setAttribute("user", userDto);

        return "Index";
    }
}


