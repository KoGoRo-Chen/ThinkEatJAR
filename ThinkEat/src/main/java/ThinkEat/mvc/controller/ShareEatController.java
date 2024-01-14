package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.EatRepoDto;
import ThinkEat.mvc.model.dto.PriceDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.model.dto.TagDto;
import ThinkEat.mvc.model.entity.Price;
import ThinkEat.mvc.model.entity.Tag;
import ThinkEat.mvc.service.EatRepoService;
import ThinkEat.mvc.service.PriceService;
import ThinkEat.mvc.service.RestaurantService;
import ThinkEat.mvc.service.TagService;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

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

    //顯示餐廳選擇表單
    @GetMapping("/Restaurant")
    public String GetRestaurantPage(Model model) {
        List<RestaurantDto> restaurantDtoList = restaurantService.getAllRestaurant();
        model.addAttribute("restaurantDtoList", restaurantDtoList);
        // 添加 restaurantDto 到模型
        model.addAttribute("restaurantDto", new RestaurantDto());
        return "ShareEat/Restaurant";
    }

    //在餐廳顯示頁面中選擇已經存在的餐廳
    @PostMapping("/Restaurant/{restaurantId}")
    public String PickThisRestaurant(@PathVariable("restaurantId") Integer restaurantId,
                                     Model model) {
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurantDto", restaurantDto);
        model.addAttribute("restaurantId", restaurantId);
        System.out.println("Choose Old RestaurantDto: " + restaurantDto);

        List<RestaurantDto> restaurantDtoList = restaurantService.getAllRestaurant();
        model.addAttribute("restaurantDtoList", restaurantDtoList);
        return "ShareEat/Restaurant";
    }

    // 顯示建立餐廳頁面
    @GetMapping("/CreateRestaurant")
    public String GetCreateRestaurantPage(Model model) {
        RestaurantDto restaurantDto = new RestaurantDto();
        // 不需要再創建一個新的 RestaurantDto
        model.addAttribute("restaurantDto", restaurantDto);
        return "ShareEat/CreateRestaurant";
    }

    // 創建餐廳
    @PostMapping("/CreateRestaurant")
    public String CreateRestaurantPage(@ModelAttribute("RestaurantDto") RestaurantDto restaurantDto,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        // 將餐廳保存到資料庫
        Integer id = restaurantService.addRestaurant(restaurantDto);
        restaurantDto.setId(id);

        // 將新增的餐廳 ID 添加到重定向 URL 的查詢字符串中
        //redirectAttributes.addFlashAttribute("restaurantDto", restaurantDto);
        redirectAttributes.addAttribute("restaurantId", id);

        return "redirect:/ThinkEat/ShareEat/Restaurant/{restaurantId}";
    }

    //從餐廳顯示頁面傳導到食記撰寫頁面
    @PostMapping("/ChooseRestaurant")
    public String ChooseRestaurant(@RequestParam("restaurantId") Integer restaurantId,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        System.out.println("restaurantId: " + restaurantId);
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurantDto", restaurantDto);

        //將新增的餐廳 ID 添加到重定向 URL 的查詢字符串中
        //redirectAttributes.addFlashAttribute("restaurantDto", restaurantDto);
        redirectAttributes.addAttribute("restaurantId", restaurantId);

        return "redirect:/ThinkEat/ShareEat/ShareEatRepo/{restaurantId}";
    }

    //顯示食記填寫表單(ShareEat)
    @GetMapping("/ShareEatRepo/{restaurantId}")
    public String GetShareEatPage(@PathVariable("restaurantId") Integer restaurantId,
                                  Model model) {
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurantDto", restaurantDto);
        List<PriceDto> prices = priceService.findAllPrice();
        model.addAttribute("prices", prices);
        List<TagDto> tags = tagService.findAllTag();
        model.addAttribute("tags", tags);

        // 只需將其添加到模型中
        model.addAttribute("eatRepoDto", new EatRepoDto());

        return "ShareEat/ShareEatRepo";
    }

    // 創建食記
    @PostMapping("/AddEatRepo")
    public String addEatRepo(@ModelAttribute("eatRepoDto") EatRepoDto eatRepoDto,
                             @RequestParam("restaurantId") Integer restaurantId,
                             @RequestParam("tagIds") List<Integer> tagIds,
                             RedirectAttributes redirectAttributes, Model model) {

        // 根據 restaurantId 獲取相應的 RestaurantDto 對象，然後將其設置到 eatRepoDto 中
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
        eatRepoDto.setRestaurant(restaurantDto);
        System.out.println("接收到restaurantDto:" + restaurantDto);

        // 處理價格
        PriceDto priceDto = priceService.getPriceById(eatRepoDto.getPriceId());
        eatRepoDto.setPrice(priceDto);

        //處理標籤
        List<TagDto> selectedTags = new ArrayList<>();
        for (Integer tagId : tagIds) {
            TagDto fetchedTag = tagService.getTagById(tagId);
            selectedTags.add(fetchedTag);
        }
        eatRepoDto.setEatRepo_TagList(selectedTags);

        // 將食記保存到資料庫
        Integer eatRepoId = eatRepoService.addEatRepo(eatRepoDto);
        System.out.println(eatRepoDto);
        model.addAttribute("eatRepoDto", eatRepoDto);

        // 將新增的食記 ID 添加到重定向 URL 的查詢字符串中
        redirectAttributes.addAttribute("eatRepoId", eatRepoId);

        // 重導到文章瀏覽頁面
        return "redirect:/ThinkEat/ViewEat/EatRepo/{eatRepoId}";
    }

    //顯示創建價格表單
    @GetMapping("/ShareEatRepo/{restaurantId}/CreateNewPrice")
    public String GetCreateNewPricePage(@PathVariable("restaurantId") Integer restaurantId,
                                        Model model) {
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurantDto", restaurantDto);
        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("priceDto", new PriceDto());

        return "ShareEat/CreateNewPrice";
    }

    // 創建價格
    @PostMapping("/ShareEatRepo/{restaurantId}/CreateNewPrice")
    public String CreateNewPrice(@ModelAttribute("priceDto") PriceDto priceDto,
                                 @RequestParam("restaurantId") Integer restaurantId,
                             RedirectAttributes redirectAttributes, Model model) {
        Price price = priceService.addPrice(priceDto);
        redirectAttributes.addAttribute("restaurantId", restaurantId);
        return "redirect:/ThinkEat/ShareEat/ShareEatRepo/{restaurantId}";
    }

    //顯示創建TAG表單
    @GetMapping("/ShareEatRepo/{restaurantId}/CreateNewTag")
    public String GetCreateNewTagPage(@PathVariable("restaurantId") Integer restaurantId,
                                      Model model) {
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurantDto", restaurantDto);
        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("tagDto", new TagDto());

        return "ShareEat/CreateNewTag";
    }

    // 創建TAG
    @PostMapping("/ShareEatRepo/{restaurantId}/CreateNewTag")
    public String CreateNewTag(@ModelAttribute("tagDto") TagDto tagDto,
                               @RequestParam("restaurantId") Integer restaurantId,
                               RedirectAttributes redirectAttributes, Model model) {
        Tag tag = tagService.addTag(tagDto);
        redirectAttributes.addAttribute("restaurantId", restaurantId);
        return "redirect:/ThinkEat/ShareEat/ShareEatRepo/{restaurantId}";
    }
}
