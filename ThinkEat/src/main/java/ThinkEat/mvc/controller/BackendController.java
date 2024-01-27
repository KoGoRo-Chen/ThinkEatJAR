package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.ShowEatPageDto;
import ThinkEat.mvc.model.dto.UserPageDto;
import ThinkEat.mvc.model.entity.*;
import ThinkEat.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Backend")
//網頁路徑：http://localhost:8080/ThinkEat/mvc/Account
public class BackendController {

    private final UserService userService;
    private final RestaurantService restaurantService;
    private final EatRepoService eatRepoService;
    private final PriceService priceService;
    private final TagService tagService;
    private final FavListService favListService;
    private final AuthorityService authorityService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public BackendController(UserService userService,
                             RestaurantService restaurantService,
                             EatRepoService eatRepoService,
                             PriceService priceService,
                             TagService tagService,
                             FavListService favListService,
                             AuthorityService authorityService,
                             BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.eatRepoService = eatRepoService;
        this.priceService = priceService;
        this.tagService = tagService;
        this.favListService = favListService;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
    }

    //顯示後台首頁
    @GetMapping("/")
    public String getAccountCenterPageForDealing(Model model) {

        return "Backend/Backend";
    }

    //顯示會員管理頁面
    @GetMapping("/User/")
    public String getUserManagementPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "12") int size,
                                        Model model) {
        //處理分頁
        Pageable pageable = PageRequest.of(page, size);
        UserPageDto userPageDto = userService.getAllUserInPage(pageable);
        model.addAttribute("userPageDto", userPageDto);
        Integer maxPage = userPageDto.getTotalPage();
        model.addAttribute("maxPage", maxPage);
        Integer curPage = userPageDto.getCurrentPage();
        model.addAttribute("curPage", curPage);

        //處理權限
        List<Authority> authorities = authorityService.findAllAuthority();
        model.addAttribute("authorities", authorities);

        return "Backend/UserManagement";
    }

    // 接受 AJAX 請求，返回用戶資訊
    @GetMapping("/GetUserInfo/")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getUserInfo(@RequestParam Integer userId) {
        // 從數據庫中查詢用戶資訊，這裡假設 userService 提供了相應的方法
        User user = userService.getUserById(userId);

        // 將用戶資訊封裝成 Map 返回給前端
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", userId);
        userInfo.put("username", user.getUsername());
        userInfo.put("nickname", user.getNickname());
        userInfo.put("password", user.getRawPassword());
        userInfo.put("authority", user.getAuthority());

        return ResponseEntity.ok(userInfo);
    }

    //更改會員資訊
    @PostMapping("/UpdateUserAccount/")
    public String updateUserAccount(@RequestParam("userId") Integer userId,
                                    @RequestParam("nickname") String newNickname,
                                    @RequestParam("password") String newPassword,
                                    @RequestParam("authority") Integer newAuthority,
                                    RedirectAttributes redirectAttributes) {
        String updateResult = userService.updateUserInformation(userId,
                newNickname,
                newPassword,
                newAuthority);
        redirectAttributes.addAttribute("updateResult", updateResult);
        return "redirect:/ThinkEat/Backend/User";

    }

    //刪除會員
    @PostMapping("/DeleteUser/")
    public String deleteUser(@RequestParam("userId") Integer userId,
                             RedirectAttributes redirectAttributes) {

        String deleteResult = userService.deleteUserById(userId);
        redirectAttributes.addAttribute("deleteResult", deleteResult);
        return "redirect:/ThinkEat/Backend/User";
    }


    //顯示餐廳管理頁面
    @GetMapping("/Restaurant/")
    public String getRestaurantManagementPage(Model model) {
        List<Restaurant> restaurantList = restaurantService.getAllRestaurant();
        model.addAttribute("restaurantList", restaurantList);

        return "Backend/RestaurantManagement";
    }

    //顯示文章管理頁面
    @GetMapping("/EatRepo/")
    public String getEatRepoManagementPage(Model model) {
        List<EatRepo> eatRepoList = eatRepoService.findAllEatRepo();
        model.addAttribute("eatRepoList", eatRepoList);

        return "Backend/EatRepoManagement";
    }

    //顯示價格管理頁面
    @GetMapping("/Price/")
    public String getPriceManagementPage(Model model) {
        List<Price> priceList = priceService.findAllPrice();
        model.addAttribute("priceList", priceList);

        return "Backend/PriceManagement";
    }

    //顯示Tag管理頁面
    @GetMapping("/Tag/")
    public String getTagManagementPage(Model model) {
        List<Tag> tagList = tagService.findAllTag();
        model.addAttribute("tagList", tagList);


        return "Backend/TagManagement";
    }

    //顯示收藏清單管理頁面
    @GetMapping("/FavList/")
    public String getFavListManagementPage(Model model) {
        List<FavList> favLists = favListService.findAllFavList();
        model.addAttribute("favLists", favLists);

        return "Backend/FavListManagement";
    }


}
