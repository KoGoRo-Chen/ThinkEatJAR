package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.EatRepoDto;
import ThinkEat.mvc.model.dto.FavListDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.model.entity.FavList;
import ThinkEat.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    //顯示收藏清單頁面(跳轉用)
    @GetMapping("/")
    public String getFavListPage(RedirectAttributes redirectAttributes) {
        Integer favListDtoId = 1;
        redirectAttributes.addAttribute("favListDtoId", favListDtoId);
        return "redirect:/ThinkEat/FavList/{favListDtoId}";
    }

    //顯示收藏清單頁面(預設序號1)
    @GetMapping("/{favListDtoId}")
    public String getFavListPageById(@PathVariable("favListDtoId") Integer favListDtoId,
                                     Model model) {
        //載入所有清單(用於側邊欄)
        List<FavListDto> favListDtoList = favListService.findAllFavList();
        model.addAttribute("favListDtoList", favListDtoList);

        //載入清單中的所有餐廳
        List<RestaurantDto> restaurantDtoList = favListService.findAllRestaurantsInFavList(favListDtoId);
        model.addAttribute("restaurantDtoList", restaurantDtoList);
        model.addAttribute("favListDtoName", favListService.getFavListById(favListDtoId).getFavListName());
        model.addAttribute("favListDtoId", favListDtoId);

        return "FavList/FavList";
    }

    //顯示創造收藏清單頁面
    @GetMapping("/CreateFavList")
    public String getCreateFavListPage(Model model) {
        FavListDto favListDto = new FavListDto();
        model.addAttribute("favListDto", favListDto);
        return "FavList/CreateFavList";
    }

    //創造收藏清單
    @PostMapping("/Create")
    public String CreateFavListPage(@ModelAttribute("favListDto") FavListDto favlistDto,
                                    RedirectAttributes redirectAttributes) {
        Integer favListDtoId = favListService.addFavList(favlistDto);
        redirectAttributes.addAttribute("favListDtoId", favListDtoId);
        return "redirect:/ThinkEat/FavList/{favListDtoId}";
    }

    //顯示編輯收藏清單頁面
    @GetMapping("/Edit/{favListDtoId}")
    public String getEditFavListPage(@PathVariable("favListDtoId") Integer favListDtoId,
                                     Model model) {
        //載入所有清單(用於側邊欄)
        List<FavListDto> favListDtoList = favListService.findAllFavList();
        model.addAttribute("favListDtoList", favListDtoList);

        //載入清單中的所有餐廳
        List<RestaurantDto> restaurantDtoList = favListService.findAllRestaurantsInFavList(favListDtoId);
        model.addAttribute("restaurantDtoList", restaurantDtoList);
        model.addAttribute("favListDto", favListService.getFavListById(favListDtoId));
        model.addAttribute("favListDtoId", favListDtoId);

        return "FavList/EditFavList";
    }

    //送出清單編輯結果
    @PostMapping("Edit/EditName/{favListDtoId}")
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



}
