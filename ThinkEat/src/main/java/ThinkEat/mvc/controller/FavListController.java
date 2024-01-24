package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.CommentDto;
import ThinkEat.mvc.model.dto.FavListDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.model.dto.ShowEatPageDto;
import ThinkEat.mvc.model.entity.FavList;
import ThinkEat.mvc.model.entity.Restaurant;
import ThinkEat.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("FavList/")
public class FavListController {
    private final PriceService priceService;
    private final TagService tagService;
    private final EatRepoService eatRepoService;
    private final RestaurantService restaurantService;
    private final FavListService favListService;

    // 使用建構子注入EatDataDao、ResDataDao、ShareEatDao
    @Autowired
    public FavListController(PriceService priceService,
                             TagService tagService,
                             EatRepoService eatRepoService,
                             RestaurantService restaurantService,
                             FavListService favListService) {

        this.priceService = priceService;
        this.tagService = tagService;
        this.eatRepoService = eatRepoService;
        this.restaurantService = restaurantService;
        this.favListService = favListService;
    }

    @ModelAttribute("favListDto")
    public FavListDto getFavListDto() {
        return new FavListDto();
    }


    //顯示收藏清單頁面(跳轉用)
    @GetMapping("/")
    public String getFavListPage(RedirectAttributes redirectAttributes) {
        //設定預設清單Id為1
        Integer favListId = 1;
        Integer newId = 0;

        if (favListService.getFavListById(favListId) == null) {
            FavList favList = new FavList();
            favList.setName("預設清單");
            newId = favListService.addFavList(favList);
            redirectAttributes.addAttribute("newId", newId);
            return "redirect:/ThinkEat/FavList/{newId}";
        } else {
            redirectAttributes.addAttribute("favListId", favListId);
            return "redirect:/ThinkEat/FavList/{favListId}";
        }

    }

    //顯示收藏清單頁面(預設序號1)
    @GetMapping("/{favListId}")
    public String getFavListPageById(@PathVariable("favListId") Integer favListId,
                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "12") int size,
                                     @ModelAttribute RestaurantDto restaurantDto,
                                     Model model) {
        if (favListId > 0) {
            //載入所有清單(用於側邊欄)
            List<FavList> favListList = favListService.findAllFavList();
            model.addAttribute("favListList", favListList);

            FavList favList = new FavList();
            model.addAttribute("favList", favList);


            //載入清單中的所有餐廳
            Pageable pageable = PageRequest.of(page, size);
            ShowEatPageDto showEatPageDto = favListService.getAllRestaurantInFavList(pageable, favListId);
            model.addAttribute("showEatPageDto", showEatPageDto);
            Integer maxPage = showEatPageDto.getTotalPage();
            model.addAttribute("maxPage", maxPage);

            Integer curPage = showEatPageDto.getCurrentPage();
            model.addAttribute("curPage", curPage);

            //獲得清單中餐廳的總數
            List<Restaurant> restaurantList = favListService.findAllRestaurantsInFavList(favListId);
            Integer totalRestaurants = restaurantList.size();
            Integer count = 0;

            model.addAttribute("count", count);
            model.addAttribute("totalRestaurants", totalRestaurants);
            model.addAttribute("restaurantList", restaurantList);
            model.addAttribute("favListName", favListService.getFavListById(favListId).getName());
            model.addAttribute("favListId", favListId);

            return "FavList/FavList";
        }

        return "redirect:/ThinkEat/FavList/";
    }

    //創造收藏清單
    @PostMapping("/Create")
    public String CreateFavListPage(@ModelAttribute("favList") FavList favlist,
                                    RedirectAttributes redirectAttributes) {
        Integer favListId = favListService.addFavList(favlist);
        redirectAttributes.addAttribute("favListId", favListId);
        return "redirect:/ThinkEat/FavList/{favListId}";
    }

    //送出清單編輯結果
    @PostMapping("/Edit/{favListDtoId}")
    public String EditFavListName(@PathVariable("favListId") Integer favListId,
                                  @RequestParam("newFavListName") String newFavListName,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        favListService.updateFavListById(favListId, newFavListName);
        redirectAttributes.addAttribute("favListId", favListId);
        return "redirect:/ThinkEat/FavList/{favListId}";
    }

    //將文章添加至收藏清單
    @PostMapping("/AddFavList/{eatRepoId}")
    public String AddFavList(@PathVariable("eatRepoId") Integer eatRepoId,
                             @RequestParam("favListId") Integer favListId,
                             RedirectAttributes redirectAttributes) {
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
    @PostMapping("/{favListDtoId}/Gacha/{count}")
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
    public String GetGachaResultPage(@ModelAttribute("selectedRestaurants") List<RestaurantDto> selectedRestaurants,
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

