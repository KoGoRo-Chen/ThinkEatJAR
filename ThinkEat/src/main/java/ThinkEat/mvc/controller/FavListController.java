package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.entity.FavList;
import ThinkEat.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("FavList/")
public class FavListController {
    private final PriceService priceService;
    private final TagService tagService;
    private final EatRepoService eatRepoService;
    private final RestaurantService restaurantService;
    private final FavListService favListServiceService;


    // 使用建構子注入EatDataDao、ResDataDao、ShareEatDao
    @Autowired
    public FavListController(PriceService priceService,
                             TagService tagService,
                             EatRepoService eatRepoService,
                             RestaurantService restaurantService, FavListService favListServiceService) {

        this.priceService = priceService;
        this.tagService = tagService;
        this.eatRepoService = eatRepoService;
        this.restaurantService = restaurantService;
        this.favListServiceService = favListServiceService;
    }

    //顯示收藏清單頁面
    @GetMapping("/")
    public String getFavListPage() {
        return "FavList/FavList";
    }

    //顯示創造收藏清單頁面
    @GetMapping("/CreateFavList")
    public String getCreateFavListPage() {
        return "FavList/CreateFavList";
    }

    //顯示編輯收藏清單頁面
    @GetMapping("/CreateFavList/{FavListID}")
    public String getEditListPage() {
        return "FavList/CreateFavList";
    }

}
