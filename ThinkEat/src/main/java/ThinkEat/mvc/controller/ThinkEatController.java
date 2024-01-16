package ThinkEat.mvc.controller;

import ThinkEat.mvc.service.EatRepoService;
import ThinkEat.mvc.service.PriceService;
import ThinkEat.mvc.service.RestaurantService;
import ThinkEat.mvc.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class ThinkEatController {
    private EatRepoService eatRepoService;
    private RestaurantService restaurantService;
    private PriceService priceService;
    private TagService tagService;

    @Autowired
    public ThinkEatController(EatRepoService eatRepoService, RestaurantService restaurantService, PriceService priceService, TagService tagService) {
        this.eatRepoService = eatRepoService;
        this.restaurantService = restaurantService;
        this.priceService = priceService;
        this.tagService = tagService;
    }

    //顯示首頁
    @GetMapping("/Index")
    public String GetIndexPage() {
        return "Index";
    }

    //顯示抽選頁面
    @GetMapping("/DecideEat")
    public String GetDecideEatPage() {
        return "DecideEat";
    }
}


