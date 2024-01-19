package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.FavListDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
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
        Integer favListDtoId = 1;
        Integer newId = 0;

        if (favListService.getFavListById(favListDtoId) == null) {
            FavListDto favListDto = new FavListDto();
            favListDto.setName("預設清單");
            newId = favListService.addFavList(favListDto);
            redirectAttributes.addAttribute("newId", newId);
            return "redirect:/ThinkEat/FavList/{newId}";
        } else {
            redirectAttributes.addAttribute("favListDtoId", favListDtoId);
            return "redirect:/ThinkEat/FavList/{favListDtoId}";
        }

    }

    //顯示收藏清單頁面(預設序號1)
    @GetMapping("/{favListDtoId}")
    public String getFavListPageById(@PathVariable("favListDtoId") Integer favListDtoId,
                                     Model model) {
        if (favListDtoId > 0) {
            //載入所有清單(用於側邊欄)
            List<FavListDto> favListDtoList = favListService.findAllFavList();
            model.addAttribute("favListDtoList", favListDtoList);

            //載入清單中的所有餐廳
            List<RestaurantDto> restaurantDtoList = favListService.findAllRestaurantsInFavList(favListDtoId);
            //獲得清單中餐廳的總數
            Integer totalRestaurants = restaurantDtoList.size();
            Integer count = 0;
            model.addAttribute("count", count);
            model.addAttribute("totalRestaurants", totalRestaurants);
            model.addAttribute("restaurantDtoList", restaurantDtoList);
            model.addAttribute("favListDtoName", favListService.getFavListById(favListDtoId).getName());
            model.addAttribute("favListDtoId", favListDtoId);

            return "FavList/FavList";
        }

        return "redirect:/ThinkEat/FavList/";
    }

    //創造收藏清單
    @PostMapping("/Create")
    public String CreateFavListPage(@ModelAttribute("favListDto") FavListDto favlistDto,
                                    RedirectAttributes redirectAttributes) {
        Integer favListDtoId = favListService.addFavList(favlistDto);
        redirectAttributes.addAttribute("favListDtoId", favListDtoId);
        return "redirect:/ThinkEat/FavList/{favListDtoId}";
    }

    //送出清單編輯結果
    @PostMapping("/Edit/{favListDtoId}")
    public String EditFavListName(@PathVariable("favListDtoId") Integer favListDtoId,
                                  @ModelAttribute("favListDto") FavListDto favListDto,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        favListService.updateFavListById(favListDtoId, favListDto);
        redirectAttributes.addAttribute("favListDtoId", favListDtoId);
        return "redirect:/ThinkEat/FavList/{favListDtoId}";
    }

    //將文章添加至收藏清單
    @PostMapping("/AddFavList/{eatRepoId}")
    public String AddFavList(@PathVariable("eatRepoId") Integer eatRepoId,
                             @RequestParam("favListId") Integer favListId,
                             RedirectAttributes redirectAttributes) {
        //以ID尋找對應清單
        FavListDto favListDto = favListService.getFavListById(favListId);

        //將食記存進清單中
        favListService.addEatRepoToFavListById(favListId, eatRepoId);

        redirectAttributes.addAttribute("eatRepoId", eatRepoId);
        redirectAttributes.addFlashAttribute("message", "添加至收藏成功");
        return "redirect:/ThinkEat/ViewEat/EatRepo/{eatRepoId}";
    }

    //在收藏清單中移除餐廳
    @PostMapping("/RemoveRestaurant/{restaurantId}")
    public String RemoveRestaurantFromList(@PathVariable("restaurantId") Integer restaurantId,
                                           @RequestParam("favListId") Integer favListDtoId,
                                           RedirectAttributes redirectAttributes) {
        //將餐廳從清單中移除
        List<RestaurantDto> restaurantDtoList = favListService.RemoveRestaurantFromFavList(favListDtoId, restaurantId);

        redirectAttributes.addFlashAttribute("restaurantDtoList", restaurantDtoList);
        redirectAttributes.addAttribute("favListDtoId", favListDtoId);

        return "redirect:/ThinkEat/FavList/{favListDtoId}";
    }

    //處理餐廳抽選
    @PostMapping("/{favListDtoId}/Gacha/{count}")
    public String PickRestaurantByCount(@RequestParam("count") Integer count,
                                        @RequestParam("favListDtoId") Integer favListDtoId,
                                        RedirectAttributes redirectAttributes,
                                        Model model) {

        FavListDto favListDto = favListService.getFavListById(favListDtoId);
        if (count <= favListDto.getFavList_EatRepoList().size()) {
            List<RestaurantDto> selectedRestaurants = favListService.PickRestaurantDtoByCount(favListDtoId, count);
            model.addAttribute("selectedRestaurants", selectedRestaurants);
        }

        return "FavList/GachaResult";
    }


    @GetMapping("/GachaResult")
    public String GetGachaResultPage(@ModelAttribute("selectedRestaurants") List<RestaurantDto> selectedRestaurants,
                                     Model model) {

        return "FavList/GachaResult";
    }


}

