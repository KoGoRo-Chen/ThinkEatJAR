package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.EatRepoDto;
import ThinkEat.mvc.model.dto.PriceDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.model.dto.TagDto;
import ThinkEat.mvc.model.entity.Restaurant;
import ThinkEat.mvc.service.EatRepoService;
import ThinkEat.mvc.service.PriceService;
import ThinkEat.mvc.service.RestaurantService;
import ThinkEat.mvc.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yaml.snakeyaml.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("ShareEat/")
public class ShareEatController {

    private final PriceService priceService;
    private final TagService tagService;
    private final EatRepoService eatRepoService;
    private final RestaurantService restaurantService;

    // 使用建構子注入EatDataDao、ResDataDao、ShareEatDao
    @Autowired
    public ShareEatController(PriceService priceService,
                              TagService tagService,
                              EatRepoService eatRepoService,
                              RestaurantService restaurantService) {

        this.priceService = priceService;
        this.tagService = tagService;
        this.eatRepoService = eatRepoService;
        this.restaurantService = restaurantService;
    }

    //顯示餐廳建立表單
    @GetMapping("/ShareRestaurant")
    public String GetShareResPage(Model model){
        // 將一個空的 RestaurantDto 添加到模型中
        RestaurantDto newRestaurantDto = new RestaurantDto();
        System.out.println("Create new RestaurantDto: " + newRestaurantDto);
        model.addAttribute("restaurantDto", newRestaurantDto);

        List<RestaurantDto> restaurantList = restaurantService.getAllRestaurant();
        model.addAttribute("restaurantList", restaurantList);
        return "ShareEat/ShareRestaurant";
    };

    // 創建餐廳
    @PostMapping("/AddRestaurant")
    public String addResData(@ModelAttribute("restaurantDto") RestaurantDto restaurantDto,
                             RedirectAttributes redirectAttributes, Model model) {
        // 將餐廳保存到資料庫
        Integer id = restaurantService.addRestaurant(restaurantDto);
        restaurantDto.setId(id);
        // 將新增的餐廳 ID 添加到重定向 URL 的查詢字符串中
        redirectAttributes.addFlashAttribute("restaurantDto", restaurantDto);
        redirectAttributes.addAttribute("restaurantId", id);
        // 重導到食記填寫表單(ShareEat)
        return "redirect:/ThinkEat/ShareEat/ShareEatRepo/{restaurantId}";
    }

    //顯示食記填寫表單(ShareEat)
    @GetMapping("/ShareEatRepo/{restaurantId}")
    public String GetShareEatPage(@PathVariable("restaurantId") Integer restaurantId,
                                  Model model) {
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);

        List<PriceDto> prices = priceService.findAllPrice();
        model.addAttribute("prices", prices);
        List<TagDto> tags = tagService.findAllTag();
        model.addAttribute("tags", tags);

        // 移除方法參數中的 EatRepoDto eatRepoDto
        // 只需將其添加到模型中
        model.addAttribute("eatRepoDto", new EatRepoDto());

        return "ShareEat/ShareEatRepo";
    };

    // 創建食記
    @PostMapping("/AddEatRepo")
    public String addEatRepo(@ModelAttribute("eatRepoDto") EatRepoDto eatRepoDto,
                             @RequestParam("restaurantId") Integer restaurantId,
                             RedirectAttributes redirectAttributes, Model model) {

        // 根據 restaurantId 獲取相應的 RestaurantDto 對象，然後將其設置到 eatRepoDto 中
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
        eatRepoDto.setRestaurant(restaurantDto);
        System.out.println("接收到restaurantDto:" + restaurantDto);

        // 處理價格
        PriceDto priceDto = priceService.getPriceById(eatRepoDto.getPrice().getId());
        eatRepoDto.setPrice(priceDto);

        //處理標籤
        List<TagDto> selectedTags = new ArrayList<>();
        for (TagDto tagDto : eatRepoDto.getEatRepo_TagList()) {
            TagDto fetchedTag = tagService.getTagById(tagDto.getId());
            selectedTags.add(fetchedTag);
        }
        eatRepoDto.setEatRepo_TagList(selectedTags);

        // 將食記保存到資料庫
        eatRepoService.addEatRepo(eatRepoDto);
        System.out.println(eatRepoDto);
        model.addAttribute("eatRepoDto", eatRepoDto);

        // 將新增的食記 ID 添加到重定向 URL 的查詢字符串中
        redirectAttributes.addFlashAttribute("eatRepoId", eatRepoDto.getId());

        // 重導到文章瀏覽頁面
        return "redirect:/ThinkEat/ViewEat/EatRepo/{eatRepoId}";
    }
}
