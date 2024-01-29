package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.RestaurantPageDto;
import ThinkEat.mvc.model.entity.*;
import ThinkEat.mvc.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("FavList/")
@SessionAttributes("presetFavListId")
public class FavListController {
    private final FavListService favListService;
    private final EatRepoService eatRepoService;
    private final UserService userService;
    private final HttpSession httpSession;

    // 使用建構子注入EatDataDao、ResDataDao、ShareEatDao
    @Autowired
    public FavListController(FavListService favListService,
                             EatRepoService eatRepoService,
                             UserService userService,
                             HttpSession httpSession) {
        this.favListService = favListService;
        this.eatRepoService = eatRepoService;
        this.userService = userService;
        this.httpSession = httpSession;
    }

    @ModelAttribute("presetFavListId")
    public Integer getPresetFavListId() {
        // 初始化 presetFavListId，您可以根據需求進行調整
        return null;
    }

    @ModelAttribute("favList")
    public FavList getFavList() {
        return new FavList();
    }


    //顯示收藏清單頁面(跳轉用)
    @GetMapping("/")
    public String getFavListPage(RedirectAttributes redirectAttributes,
                                 @SessionAttribute(name = "presetFavListId", required = false) Integer presetFavListId) {

        //找出會員資訊
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            if (presetFavListId == null) {
                //處理訪客邏輯
                FavList favList = new FavList();
                favList.setName("訪客預設清單");
                Integer Id = favListService.addGuestList(favList);

                httpSession.setAttribute("presetFavListId", Id);
                return "redirect:/ThinkEat/FavList/GuestList/";
            } else {
                //訪客已經有預設清單，導入至預設清單
                return "redirect:/ThinkEat/FavList/GuestList/";
            }

        } else {
            redirectAttributes.addAttribute("userId", user.getId());
            redirectAttributes.addAttribute("favListCount", 1);

            return "redirect:/ThinkEat/FavList/{userId}/List/{favListCount}/";

        }
    }

    //顯示訪客用清單
    @GetMapping("/GuestList/")
    public String getGuestListPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "12") int size,
                                   @ModelAttribute Restaurant restaurant,
                                   @SessionAttribute(name = "presetFavListId", required = false) Integer presetFavListId,
                                   Model model) {
        // 根據清單 ID 獲取相關資訊，並將其添加到 Model 中
        if (presetFavListId == null) {
            return "redirect:/ThinkEat/FavList/";
        }
        FavList guestFavList = favListService.getFavListById(presetFavListId);
        // 將清單資訊添加到模型中
        model.addAttribute("favListList", guestFavList);

        FavList favList = new FavList();
        model.addAttribute("favList", favList);

        //載入清單中的所有餐廳
        Pageable pageable = PageRequest.of(page, size);
        RestaurantPageDto restaurantPageDto = favListService.getAllRestaurantInFavList(pageable, presetFavListId);
        model.addAttribute("restaurantPageDto", restaurantPageDto);

        Integer maxPage = restaurantPageDto.getTotalPage();
        model.addAttribute("maxPage", maxPage);

        Integer curPage = restaurantPageDto.getCurrentPage();
        model.addAttribute("curPage", curPage);

        //獲得清單中餐廳的總數
        List<Restaurant> restaurantList = favListService.findAllRestaurantsInFavList(presetFavListId);
        Integer totalRestaurants = restaurantList.size();
        Integer count = 0;

        model.addAttribute("count", count);
        model.addAttribute("totalRestaurants", totalRestaurants);
        model.addAttribute("restaurantList", restaurantList);
        model.addAttribute("favListName", favListService.getFavListById(presetFavListId).getName());
        model.addAttribute("favListId", presetFavListId);

        return "FavList/GuestList";
    }

    //顯示收藏清單頁面(預設序號1)
    @GetMapping("/{userId}/List/{listCount}/")
    public String getFavListPageById(@PathVariable("userId") Integer userId,
                                     @PathVariable("listCount") Integer listCount,
                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "12") int size,
                                     RedirectAttributes redirectAttributes,
                                     Authentication authentication,
                                     Model model) {
        // 檢查會員 Session 是否過期
        if (authentication == null || !authentication.isAuthenticated()) {
            // 如果 Session 過期，導回登入頁面並顯示錯誤訊息
            redirectAttributes.addFlashAttribute("SessionExpiredError", "會員 Session 已過期，請重新登入。");
            return "redirect:/ThinkEat/Login";
        }

        //找出會員資訊
        User user = userService.getUserById(userId);
        FavList favList = new FavList();
        model.addAttribute("favList", favList);

        //載入所有清單(用於側邊欄)
        List<FavList> favListList = user.getFavLists();
        model.addAttribute("favListList", favListList);

        FavList thisFavList = favListService.findFavListByUserAndListCount(userId, listCount);
        Integer favListId = thisFavList.getId();

        //載入清單中的所有餐廳
        Pageable pageable = PageRequest.of(page, size);
        RestaurantPageDto restaurantPageDto = favListService.getAllRestaurantInFavList(pageable, favListId);
        model.addAttribute("restaurantPageDto", restaurantPageDto);
        Integer maxPage = restaurantPageDto.getTotalPage();
        model.addAttribute("maxPage", maxPage);

        Integer curPage = restaurantPageDto.getCurrentPage();
        model.addAttribute("curPage", curPage);

        //獲得清單中餐廳的總數
        List<Restaurant> restaurantList = favListService.findAllRestaurantsInFavList(favListId);
        Integer totalRestaurants = restaurantList.size();
        Integer count = 0;

        model.addAttribute("listCount", listCount);
        model.addAttribute("userId", userId);
        model.addAttribute("count", count);
        model.addAttribute("totalRestaurants", totalRestaurants);
        model.addAttribute("restaurantList", restaurantList);
        model.addAttribute("favListName", favListService.getFavListById(favListId).getName());
        model.addAttribute("favListId", favListId);

        return "FavList/FavList";
    }

    //創造收藏清單
    @PostMapping("/Create")
    public String CreateFavListPage(@RequestParam("name") String name,
                                    @RequestParam("userId") Integer userId,
                                    RedirectAttributes redirectAttributes,
                                    Authentication authentication,
                                    Model model) {

        // 檢查會員 Session 是否過期
        if (authentication == null || !authentication.isAuthenticated()) {
            // 如果 Session 過期，導回登入頁面並顯示錯誤訊息
            redirectAttributes.addFlashAttribute("SessionExpiredError", "會員 Session 已過期，請重新登入。");
            return "redirect:/ThinkEat/Login";
        }

        //建立一個新的Favlist
        FavList favList = new FavList();
        favList.setName(name);

        //找出會員資訊
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findUserByUsername(username);
        favList.setFavList_User(user);

        Integer favListId = favListService.addFavList(favList);
        model.addAttribute("favListId", favListId);
        return "FavList/FavList";
    }

    //送出清單編輯結果
    @PostMapping("/EditList")
    public String EditList(@RequestParam("listCount") Integer listCount,
                           @RequestParam("userId") Integer userId,
                           @RequestParam("name") String newFavListName,
                           RedirectAttributes redirectAttributes,
                           Authentication authentication,
                           Model model) {
        // 檢查會員 Session 是否過期
        if (authentication == null || !authentication.isAuthenticated()) {
            // 如果 Session 過期，導回登入頁面並顯示錯誤訊息
            redirectAttributes.addFlashAttribute("SessionExpiredError", "會員 Session 已過期，請重新登入。");
            return "redirect:/ThinkEat/Login";
        }
        FavList favList = favListService.findFavListByUserAndListCount(userId, listCount);
        favListService.updateFavListById(favList.getId(), newFavListName);
        redirectAttributes.addAttribute("userId", userId);
        redirectAttributes.addAttribute("listCount", listCount);
        return "redirect:/ThinkEat/FavList/{userId}/List/{listCount}/";
    }

    //將文章添加至收藏清單
    @PostMapping("/AddFavList/{eatRepoId}")
    public String AddFavList(@PathVariable("eatRepoId") Integer eatRepoId,
                             @RequestParam("favListId") Integer favListId,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        //以ID尋找對應清單
        FavList favList = favListService.getFavListById(favListId);

        //將食記存進清單中
        favListService.addEatRepoToFavListById(favListId, eatRepoId);

        redirectAttributes.addAttribute("eatRepoId", eatRepoId);
        redirectAttributes.addFlashAttribute("message", "添加至收藏成功");
        return "redirect:/ThinkEat/ViewEat/EatRepo/{eatRepoId}";
    }

    //在收藏清單中移除餐廳
    @PostMapping("/RemoveRestaurant/{restaurantId}")
    public String RemoveRestaurantFromList(@PathVariable("restaurantId") Integer restaurantId,
                                           @RequestParam("favListId") Integer favListId,
                                           RedirectAttributes redirectAttributes) {
        //將餐廳從清單中移除
        List<Restaurant> restaurantList = favListService.removeRestaurantFromFavList(favListId, restaurantId);

        redirectAttributes.addFlashAttribute("restaurantList", restaurantList);
        redirectAttributes.addAttribute("favListId", favListId);

        return "redirect:/ThinkEat/FavList/{favListId}";
    }

    //處理餐廳抽選
    @PostMapping("/{favListId}/Gacha/{count}")
    public String PickRestaurantByCount(@RequestParam("count") Integer count,
                                        @RequestParam("favListId") Integer favListId,
                                        RedirectAttributes redirectAttributes,
                                        Model model) {

        FavList favList = favListService.getFavListById(favListId);
        if (count <= favList.getFavList_EatRepoList().size()) {
            List<Restaurant> selectedRestaurants = favListService.pickRestaurantsByCount(favListId, count);
            model.addAttribute("selectedRestaurants", selectedRestaurants);
        }

        return "FavList/GachaResult";
    }


    @GetMapping("/GachaResult")
    public String GetGachaResultPage(@ModelAttribute("selectedRestaurants") List<Restaurant> selectedRestaurants,
                                     Model model) {

        return "FavList/GachaResult";
    }

    //刪除收藏清單
    @PostMapping("/DeleteFavList")
    public String DeleteFavList(@RequestParam("favListId") Integer favListId,
                                RedirectAttributes redirectAttributes) {
        //找到留言
        FavList favList = favListService.getFavListById(favListId);

        //執行刪除
        favListService.deleteFavListById(favListId);

        // 返回清單頁面
        return "redirect:/ThinkEat/FavList/";
    }



}

