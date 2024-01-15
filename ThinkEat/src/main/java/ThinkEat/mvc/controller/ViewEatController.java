package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.EatRepoDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.service.EatRepoService;
import ThinkEat.mvc.service.PriceService;
import ThinkEat.mvc.service.RestaurantService;
import ThinkEat.mvc.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("ViewEat/")
public class ViewEatController {

    private final PriceService priceService;
    private final TagService tagService;
    private final EatRepoService eatRepoService;
    private final RestaurantService restaurantService;

    @Autowired
    public ViewEatController(PriceService priceService,
                             TagService tagService,
                             EatRepoService eatRepoService,
                             RestaurantService restaurantService) {
        this.priceService = priceService;
        this.tagService = tagService;
        this.eatRepoService = eatRepoService;
        this.restaurantService = restaurantService;
    }

    //顯示ShowEat頁面(顯示所有餐廳)
    @GetMapping("/ShowEat")
    public String GetShowEatPage(Model model) {
        List<RestaurantDto> restaurantDtoList = restaurantService.getAllRestaurant();
        System.out.println(restaurantDtoList);
        model.addAttribute("restaurantDtoList", restaurantDtoList);
        return "ViewEat/ShowEat";
    }

    ;

    //顯示ViewEat/Res/{ResId}/頁面
    @GetMapping("/ResInfo/{restaurantId}")
    public String getResPage(@PathVariable("restaurantId") Integer restaurantId, Model model) {
        // 1. 根據 restaurantId 從數據庫中檢索相應的 餐廳
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
        List<EatRepoDto> eatRepoDto = restaurantService.getAllEatRepoByRestaurantId(restaurantId);

        // 2. 將檢索到的 餐廳 及 擁有的食記 添加到模型中
        model.addAttribute("restaurantDto", restaurantDto);
        model.addAttribute("eatRepoDto", eatRepoDto);

        // 返回 ViewEat 頁面
        return "ViewEat/ResInfo";
    }

    ;

    //顯示ViewEat/EatRepo/{eatRepoId}頁面
    @GetMapping("/EatRepo/{eatRepoId}")
    public String GetViewEatPage(@PathVariable("eatRepoId") Integer eatRepoId, Model model) {
        // 1. 根據 eatRepoId 從數據庫中檢索相應的 食記
        EatRepoDto eatRepoDto = eatRepoService.getEatRepoByEatRepoId(eatRepoId);
        System.out.println("ViewEat頁面顯示eatRepoDto: " + eatRepoDto);
        model.addAttribute("eatRepoDto", eatRepoDto);
        System.out.println("新增成功" + eatRepoDto);
        model.addAttribute("restaurantId", eatRepoDto.getRestaurant().getId());

        // 返回 ViewEat 頁面
        return "ViewEat/EatRepo";
    }

    ;


}
