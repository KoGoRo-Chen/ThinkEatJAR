package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.*;
import ThinkEat.mvc.model.entity.*;
import ThinkEat.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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
        System.out.println("接收到的用戶Id為:" + userId);

        // 從數據庫中查詢用戶資訊，這裡假設 userService 提供了相應的方法
        User user = userService.getUserById(userId);


        // 將用戶資訊封裝成 Map 返回給前端
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", user.getId());
        userInfo.put("userIdforDelete", user.getId());
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
        return "redirect:/ThinkEat/Backend/User/";

    }

    //刪除會員
    @PostMapping("/DeleteUser")
    public String deleteUser(@RequestParam("userIdforDelete") Integer userId,
                             RedirectAttributes redirectAttributes) {

        String deleteResult = userService.deleteUserById(userId);
        return "redirect:/ThinkEat/Backend/User/";
    }


    //顯示餐廳管理頁面
    @GetMapping("/Restaurant/")
    public String getRestaurantManagementPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "12") int size,
                                              Model model) {
        Pageable pageable = PageRequest.of(page, size);
        RestaurantPageDto restaurantPageDto = restaurantService.getAllRestaurant(pageable);
        model.addAttribute("restaurantPageDto", restaurantPageDto);

        Integer maxPage = restaurantPageDto.getTotalPage();
        model.addAttribute("maxPage", maxPage);

        Integer curPage = restaurantPageDto.getCurrentPage();
        model.addAttribute("curPage", curPage);


        return "Backend/RestaurantManagement";
    }

    // 接受 AJAX 請求，返回餐廳資訊
    @GetMapping("/GetRestaurantInfo/")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getRestaurantInfo(@RequestParam Integer restaurantId) {
        // 從數據庫中查詢餐廳資訊
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        // 收集餐廳圖片網址
        List<String> restaurantPicPathList = new ArrayList<>();
        for (Picture picture : restaurant.getResPicList()) {
            restaurantPicPathList.add(picture.getHtmlPath());
        }

        // 將餐廳資訊封裝成 Map 返回給前端
        Map<String, Object> restaurantInfo = new HashMap<>();
        restaurantInfo.put("restaurantId", restaurantId);
        restaurantInfo.put("restaurantName", restaurant.getName());
        restaurantInfo.put("restaurantAddress", restaurant.getAddress());
        restaurantInfo.put("restaurantPicPathList", restaurantPicPathList);

        return ResponseEntity.ok(restaurantInfo);
    }

    // 創建餐廳
    @PostMapping("/CreateRestaurant/")
    public String CreateRestaurantPage(@RequestParam("name") String name,
                                       @RequestParam("address") String address) {
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName(name);
        newRestaurant.setAddress(address);

        // 將餐廳保存到資料庫
        Integer id = restaurantService.addRestaurant(newRestaurant);

        return "redirect:/ThinkEat/Backend/Restaurant/";
    }

    //更改餐廳資訊
    @PostMapping("/UpdateRestaurant/")
    public String updateRestaurant(@RequestParam("restaurantId") Integer restaurantId,
                                   @RequestParam("restaurantName") String restaurantName,
                                   @RequestParam("restaurantAddress") String restaurantAddress,
                                   RedirectAttributes redirectAttributes) {
        String updateResult = restaurantService.updateRestaurant(restaurantId, restaurantName, restaurantAddress);
        return "redirect:/ThinkEat/Backend/Restaurant/";

    }

    //刪除餐廳
    @PostMapping("/DeleteRestaurant/")
    public String deleteRestaurant(@RequestParam("restaurantId") Integer restaurantId,
                                   RedirectAttributes redirectAttributes) {

        String deleteResult = restaurantService.deleteRestaurant(restaurantId);
        return "redirect:/ThinkEat/Backend/Restaurant/";
    }

    //顯示文章管理頁面
    @GetMapping("/EatRepo/")
    public String getEatRepoManagementPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "12") int size,
                                           Model model) {
        Pageable pageable = PageRequest.of(page, size);
        EatRepoPageDto eatRepoPageDto = eatRepoService.getAllEatRepoPagination(pageable);
        model.addAttribute("eatRepoPageDto", eatRepoPageDto);

        Integer maxPage = eatRepoPageDto.getTotalPage();
        model.addAttribute("maxPage", maxPage);

        Integer curPage = eatRepoPageDto.getCurrentPage();
        model.addAttribute("curPage", curPage);

        return "Backend/EatRepoManagement";
    }

    //刪除文章
    @PostMapping("/DeleteEatRepo/")
    public String deleteEatRepo(@RequestParam("eatRepoId") Integer eatRepoId,
                                RedirectAttributes redirectAttributes) {

        String deleteResult = eatRepoService.deleteEatRepo(eatRepoId);
        return "redirect:/ThinkEat/Backend/EatRepo/";
    }

    //顯示價格管理頁面
    @GetMapping("/Price/")
    public String getPriceManagementPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "12") int size,
                                         Model model) {
        Pageable pageable = PageRequest.of(page, size);
        PricePageDto pricePageDto = priceService.getAllPriceInPage(pageable);
        model.addAttribute("pricePageDto", pricePageDto);

        Integer maxPage = pricePageDto.getTotalPage();
        model.addAttribute("maxPage", maxPage);

        Integer curPage = pricePageDto.getCurrentPage();
        model.addAttribute("curPage", curPage);

        return "Backend/PriceManagement";
    }

    // 接受 AJAX 請求，返回價格資訊
    @GetMapping("/GetPriceInfo/")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getPriceInfo(@RequestParam Integer priceId) {
        // 從數據庫中查詢餐廳資訊
        Price price = priceService.getPriceById(priceId);

        // 將餐廳資訊封裝成 Map 返回給前端
        Map<String, Object> priceInfo = new HashMap<>();
        priceInfo.put("priceId", priceId);
        priceInfo.put("priceName", price.getName());

        return ResponseEntity.ok(priceInfo);
    }

    // 增加新價格
    @PostMapping("/CreateNewPrice/")
    public String CreateNewPrice(@RequestParam("name") String name) {
        // 使用新價格的名稱設置PriceDto
        Price newPrice = new Price();
        newPrice.setName(name);

        // 將新價格添加到數據庫
        Integer priceId = priceService.addPrice(newPrice);

        return "redirect:/ThinkEat/Backend/Price/";
    }

    //變更價位
    @PostMapping("/UpdatePrice/")
    public String updatePrice(@RequestParam("priceId") Integer priceId,
                              @RequestParam("priceName") String newPricename,
                              RedirectAttributes redirectAttributes) {
        String updateResult = priceService.updatePriceById(priceId, newPricename);
        return "redirect:/ThinkEat/Backend/Price";

    }

    //刪除價位
    @PostMapping("/DeletePrice/")
    public String deletePrice(@RequestParam("priceId") Integer priceId,
                              RedirectAttributes redirectAttributes) {

        String deleteResult = priceService.deletePrice(priceId);
        return "redirect:/ThinkEat/Backend/Price/";
    }

    //顯示Tag管理頁面
    @GetMapping("/Tag/")
    public String getTagManagementPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "size", defaultValue = "12") int size,
                                       Model model) {
        Pageable pageable = PageRequest.of(page, size);
        TagPageDto tagPageDto = tagService.getAllTagInPage(pageable);
        model.addAttribute("tagPageDto", tagPageDto);

        Integer maxPage = tagPageDto.getTotalPage();
        model.addAttribute("maxPage", maxPage);

        Integer curPage = tagPageDto.getCurrentPage();
        model.addAttribute("curPage", curPage);

        return "Backend/TagManagement";
    }

    // 接受 AJAX 請求，返回Tag資訊
    @GetMapping("/GetTagInfo/")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTagInfo(@RequestParam Integer tagId) {
        // 從數據庫中查詢餐廳資訊
        Tag tag = tagService.getTagById(tagId);

        // 將餐廳資訊封裝成 Map 返回給前端
        Map<String, Object> priceInfo = new HashMap<>();
        priceInfo.put("tagId", tagId);
        priceInfo.put("tagName", tag.getName());

        return ResponseEntity.ok(priceInfo);
    }

    // 增加新TAG
    @PostMapping("/CreateNewTag/")
    public String CreateNewTag(@RequestParam("name") String name) {
        // 使用新價格的名稱設置PriceDto
        Tag newTag = new Tag();
        newTag.setName(name);

        // 將新價格添加到數據庫
        Integer tagId = tagService.addTag(newTag);

        return "redirect:/ThinkEat/Backend/Tag/";
    }

    //變更標籤
    @PostMapping("/UpdateTag/")
    public String updateTag(@RequestParam("tagId") Integer tagId,
                            @RequestParam("tagName") String newTagname,
                            RedirectAttributes redirectAttributes) {
        String updateResult = tagService.updateTagByTagId(tagId, newTagname);
        redirectAttributes.addAttribute("updateResult", updateResult);
        return "redirect:/ThinkEat/Backend/Tag/";

    }

    //刪除標籤
    @PostMapping("/DeleteTag/")
    public String deleteTag(@RequestParam("tagId") Integer tagId,
                            RedirectAttributes redirectAttributes) {

        String deleteResult = tagService.deleteTag(tagId);
        return "redirect:/ThinkEat/Backend/Tag/";
    }

    //跳轉至用戶收藏清單頁面
    @PostMapping("/MoveToUserFavList/")
    public String MoveToUserFavListPage(@RequestParam("userId") Integer userId,
                                        RedirectAttributes redirectAttributes) {
        //找出會員資訊
        User user = userService.getUserById(userId);

        redirectAttributes.addAttribute("userId", userId);
        redirectAttributes.addAttribute("listCount", 1);
        return "redirect:/ThinkEat/FavList/{userId}/List/{listCount}/";
    }


}
