package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.*;
import ThinkEat.mvc.model.entity.Price;
import ThinkEat.mvc.model.entity.Tag;
import ThinkEat.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    private final PictureService pictureService;

    // 建構子注入依賴
    @Autowired
    public ShareEatController(PriceService priceService,
                              TagService tagService,
                              EatRepoService eatRepoService,
                              RestaurantService restaurantService,
                              PictureService pictureService) {

        this.priceService = priceService;
        this.tagService = tagService;
        this.eatRepoService = eatRepoService;
        this.restaurantService = restaurantService;
        this.pictureService = pictureService;
    }

    //顯示餐廳選擇表單
    @GetMapping("/Restaurant")
    public String GetRestaurantPage(Model model) {
        // 添加 restaurantDto 到模型
        model.addAttribute("restaurantDto", new RestaurantDto());
        List<RestaurantDto> restaurantDtoList = restaurantService.getAllRestaurant();
        if (restaurantDtoList != null) {
            model.addAttribute("restaurantDtoList", restaurantDtoList);
        } else {
            return "redirect:/ThinkEat/ShareEat/Restaurant/CreateRestaurant";
        }

        return "ShareEat/Restaurant";
    }

    //在餐廳顯示頁面中選擇已經存在的餐廳
    @GetMapping("/Restaurant/{restaurantId}")
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
    @GetMapping("/Restaurant/CreateRestaurant")
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

        //創建一個新食記，存入餐廳資料並取得ID
        EatRepoDto eatRepoDto = new EatRepoDto();
        eatRepoDto.setRestaurant(restaurantDto);
        Integer eatRepoId = eatRepoService.addEatRepoOnlyHaveRestaurant(eatRepoDto);
        System.out.println("建立新的eatRepod，Id為:" + eatRepoId);
        model.addAttribute("eatRepoDto", eatRepoDto);
        model.addAttribute("eatRepoId", eatRepoId);

        //將新增的餐廳 ID 添加到重定向 URL 的查詢字符串中
        redirectAttributes.addAttribute("restaurantId", restaurantId);
        redirectAttributes.addAttribute("eatRepoId", eatRepoId);

        return "redirect:/ThinkEat/ShareEat/ShareEatRepo/{restaurantId}/EatRepoId/{eatRepoId}";
    }

    //顯示食記填寫表單(ShareEat)
    @GetMapping("/ShareEatRepo/{restaurantId}/EatRepoId/{eatRepoId}")
    public String GetEatRepoPage(@PathVariable("restaurantId") Integer restaurantId,
                                 @PathVariable("eatRepoId") Integer eatRepoId,
                                 Model model) {
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurantDto", restaurantDto);
        List<PriceDto> prices = priceService.findAllPrice();
        model.addAttribute("prices", prices);
        List<TagDto> tags = tagService.findAllTag();
        model.addAttribute("tags", tags);

        EatRepoDto eatRepoDto = eatRepoService.getEatRepoByEatRepoId(eatRepoId);
        model.addAttribute("eatRepoDto", eatRepoDto);



        return "ShareEat/ShareEatRepo";
    }


    // 將其餘資訊放入同篇食記
    @PostMapping("/AddEatRepo")
    public String addEatRepo(@ModelAttribute("eatRepoDto") EatRepoDto eatRepoDto,
                             @RequestParam("restaurantId") Integer restaurantId,
                             @RequestParam("eatRepoId") Integer eatRepoId,
                             @RequestParam("tagIds") List<Integer> tagIds,
                             @RequestPart("multipartFileList") List<MultipartFile> multipartFileList,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        System.out.println(eatRepoDto);

        // 根據 restaurantId 獲取相應的 RestaurantDto 對象，然後將其設置到 eatRepoDto 中
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
        eatRepoDto.setRestaurant(restaurantDto);
        System.out.println("接收到restaurantDto:" + restaurantDto);

        //處理多張圖片
        for (MultipartFile multipartFile : multipartFileList) {
            PictureDto pictureDto = new PictureDto();
            pictureDto.setPic_EatRepo(eatRepoService.getEatRepoByEatRepoId(eatRepoId));
            Integer picDtoId = pictureService.addPicture(pictureDto, multipartFile);
            pictureDto.setId(picDtoId);
            eatRepoDto.getPicList().add(pictureDto);
        }
        System.out.println(eatRepoDto.getPicIdList());

        //處理價格
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
        eatRepoService.updateEatRepoByEatRepoId(eatRepoId,eatRepoDto);
        System.out.println(eatRepoDto);
        model.addAttribute("eatRepoDto", eatRepoDto);


        // 將食記 ID 添加到重定向 URL 的查詢字符串中
        redirectAttributes.addAttribute("eatRepoId", eatRepoId);

        // 重導到文章瀏覽頁面
        return "redirect:/ThinkEat/ViewEat/EatRepo/{eatRepoId}";
    }


    //顯示食記編輯表單
    @GetMapping("/ShareEatRepo/Edit/{eatRepoId}")
    public String GetEditEatRepoPage(@PathVariable("eatRepoId") Integer eatRepoId,
                                     Model model) {
        model.addAttribute("eatRepoDto", eatRepoService.getEatRepoByEatRepoId(eatRepoId));
        System.out.println("編輯頁面接收到的eatRepo: " + eatRepoService.getEatRepoByEatRepoId(eatRepoId));
        List<PriceDto> prices = priceService.findAllPrice();
        model.addAttribute("prices", prices);
        List<TagDto> tags = tagService.findAllTag();
        model.addAttribute("tags", tags);

        return "ShareEat/Edit";
    }

    // 處理編輯頁面提交
    @PostMapping("/EditEatRepo")
    public String editEatRepo(@RequestParam("eatRepoId") Integer eatRepoId,
                              @RequestParam("tagIds") List<Integer> tagIds,
                              @RequestParam("tagIds") Integer priceId,
                              @ModelAttribute("eatRepoDto") EatRepoDto eatRepoDto,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // 處理價格
        PriceDto priceDto = priceService.getPriceById(priceId);
        eatRepoDto.setPrice(priceDto);

        //處理標籤
        List<TagDto> selectedTags = new ArrayList<>();
        for (Integer tagId : tagIds) {
            TagDto fetchedTag = tagService.getTagById(tagId);
            selectedTags.add(fetchedTag);
        }
        eatRepoDto.setEatRepo_TagList(selectedTags);

        // 處理編輯的邏輯
        System.out.println("編輯頁面接收到的資料:" + eatRepoDto);
        eatRepoService.updateEatRepoByEatRepoId(eatRepoId, eatRepoDto);
        eatRepoDto.setId(eatRepoId);
        System.out.println("更新後的資料:" + eatRepoDto);

        redirectAttributes.addAttribute("eatRepoId", eatRepoId);

        // 重定向到食記查看頁面，這裡使用/{eatRepoId}作為占位符，根據實際情況修改
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
