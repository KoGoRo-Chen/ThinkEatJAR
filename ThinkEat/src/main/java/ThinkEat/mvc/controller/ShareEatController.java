package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.*;
import ThinkEat.mvc.model.entity.Price;
import ThinkEat.mvc.model.entity.Tag;
import ThinkEat.mvc.model.entity.User;
import ThinkEat.mvc.model.entity.UserDetails;
import ThinkEat.mvc.service.*;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    private final UserService userService;

    // 建構子注入依賴
    @Autowired
    public ShareEatController(PriceService priceService,
                              TagService tagService,
                              EatRepoService eatRepoService,
                              RestaurantService restaurantService,
                              PictureService pictureService,
                              UserService userService,
                              ModelMapper modelMapper) {

        this.priceService = priceService;
        this.tagService = tagService;
        this.eatRepoService = eatRepoService;
        this.restaurantService = restaurantService;
        this.pictureService = pictureService;
        this.userService = userService;
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

    // 創建餐廳
    @PostMapping("/CreateRestaurant")
    public String CreateRestaurantPage(RestaurantDto restaurantDto,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        RestaurantDto newRestaurantDto = new RestaurantDto();
        model.addAttribute("newRestaurantDto", newRestaurantDto);
        newRestaurantDto.setName(restaurantDto.getName());
        newRestaurantDto.setAddress(restaurantDto.getAddress());

        // 將餐廳保存到資料庫
        Integer id = restaurantService.addRestaurant(newRestaurantDto);
        restaurantDto.setId(id);

        // 將新增的餐廳 ID 添加到重定向 URL 的查詢字符串中
        redirectAttributes.addAttribute("restaurantId", id);

        return "redirect:/ThinkEat/ShareEat/Restaurant/{restaurantId}";
    }

    //從餐廳顯示頁面傳導到食記撰寫頁面
    @PostMapping("/ChooseRestaurant")
    public String ChooseRestaurant(@RequestParam("restaurantId") Integer restaurantId,
                                   RedirectAttributes redirectAttributes,
                                   Authentication authentication,
                                   Model model) {

        //創建一個新食記
        EatRepoDto eatRepoDto = new EatRepoDto();

        //獲取餐廳資料並存入
        System.out.println("restaurantId: " + restaurantId);
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurantDto", restaurantDto);
        eatRepoDto.setRestaurant(restaurantDto);

        //獲取會員資料並存入
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("username為" + username);
        UserDto existingUser = userService.findUserByUsername(username);
        System.out.println("user" + existingUser);
        if (existingUser == null) {
            return "redirect:/ThinkEat/Login";
        } else {
            eatRepoDto.setEatRepo_User(existingUser);
        }

        Integer eatRepoId = eatRepoService.addEatRepoOnlyHaveRestaurant(eatRepoDto);
        System.out.println("建立新的eatRepod，Id為:" + eatRepoId);
        model.addAttribute("eatRepoDto", eatRepoDto);
        model.addAttribute("eatRepoId", eatRepoId);

        redirectAttributes.addAttribute("eatRepoId", eatRepoId);

        return "redirect:/ThinkEat/ShareEat/ShareEatRepo/{eatRepoId}";
    }

    //顯示食記填寫表單(ShareEat)
    @GetMapping("/ShareEatRepo/{eatRepoId}")
    public String GetEatRepoPage(@PathVariable("eatRepoId") Integer eatRepoId,
                                 Model model) {
        RestaurantDto restaurantDto = eatRepoService.getEatRepoByEatRepoId(eatRepoId).getRestaurant();
        model.addAttribute("restaurantDto", restaurantDto);

        //價格
        List<PriceDto> prices = priceService.findAllPrice();
        model.addAttribute("prices", prices);
        PriceDto priceDto = new PriceDto();
        model.addAttribute("priceDto", priceDto);

        //標籤
        List<TagDto> tags = tagService.findAllTag();
        model.addAttribute("tags", tags);
        TagDto tagDto = new TagDto();
        model.addAttribute("tagDto", tagDto);

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
            pictureDto.setPic_Restaurant(restaurantService.getRestaurantById(restaurantId));
            Integer picDtoId = pictureService.addPicture(pictureDto, multipartFile);
            pictureDto.setId(picDtoId);
            eatRepoDto.getPicList().add(pictureDto);
        }

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

    // 增加新價格
    @PostMapping("/CreateNewPrice")
    public String CreateNewPrice(@ModelAttribute("priceDto") PriceDto newPriceDto,
                                 @RequestParam("restaurantId") Integer restaurantId,
                                 @RequestParam("eatRepoId") Integer eatRepoId,
                                 RedirectAttributes redirectAttributes, Model model) {
        // 使用新價格的名稱設置PriceDto
        newPriceDto.setName(newPriceDto.getName());

        // 將新價格添加到數據庫
        Price price = priceService.addPrice(newPriceDto);

        // 重定向到相應的頁面
        redirectAttributes.addAttribute("restaurantId", restaurantId);
        redirectAttributes.addAttribute("eatRepoId", eatRepoId);
        return "redirect:/ThinkEat/ShareEat/ShareEatRepo/{restaurantId}/EatRepoId/{eatRepoId}";
    }

    // 增加新TAG
    @PostMapping("/CreateNewTag")
    public String CreateNewTag(@ModelAttribute("tagDto") TagDto newTagDto,
                               @RequestParam("restaurantId") Integer restaurantId,
                               @RequestParam("eatRepoId") Integer eatRepoId,
                               RedirectAttributes redirectAttributes, Model model) {
        // 使用新Tag的名稱設置tagDto
        newTagDto.setName(newTagDto.getName());

        // 將新價格添加到數據庫
        Tag tag = tagService.addTag(newTagDto);

        // 重定向到相應的頁面
        redirectAttributes.addAttribute("restaurantId", restaurantId);
        redirectAttributes.addAttribute("eatRepoId", eatRepoId);
        return "redirect:/ThinkEat/ShareEat/ShareEatRepo/{restaurantId}/EatRepoId/{eatRepoId}";
    }



    //顯示食記編輯表單
    @GetMapping("/ShareEatRepo/Edit/{eatRepoId}")
    public String GetEditEatRepoPage(@PathVariable("eatRepoId") Integer eatRepoId,
                                     Model model) {
        RestaurantDto restaurantDto = eatRepoService.getEatRepoByEatRepoId(eatRepoId).getRestaurant();
        model.addAttribute("restaurantDto", restaurantDto);

        //價格
        List<PriceDto> prices = priceService.findAllPrice();
        model.addAttribute("prices", prices);
        PriceDto priceDto = new PriceDto();
        model.addAttribute("priceDto", priceDto);

        //標籤
        List<TagDto> tags = tagService.findAllTag();
        model.addAttribute("tags", tags);
        TagDto tagDto = new TagDto();
        model.addAttribute("tagDto", tagDto);

        EatRepoDto eatRepoDto = eatRepoService.getEatRepoByEatRepoId(eatRepoId);
        model.addAttribute("eatRepoDto", eatRepoDto);

        return "ShareEat/Edit";
    }

    //刪除文章
    @PostMapping("/ShareEatRepo/Delete/{eatRepoId}")
    public String CreateNewTag(@PathVariable("eatRepoId") Integer eatRepoId,
                               RedirectAttributes redirectAttributes) {
        //以ID找到正確的eatRepo
        EatRepoDto eatRepoDto = eatRepoService.getEatRepoByEatRepoId(eatRepoId);

        //找到對應的餐廳，並把餐廳ID加入重新導向功能
        RestaurantDto restaurantDto = eatRepoDto.getRestaurant();
        Integer restaurantId = restaurantDto.getId();
        redirectAttributes.addAttribute("restaurantId", restaurantId);

        //service執行刪除
        eatRepoService.delete(eatRepoId);

        return "redirect:/ThinkEat/ViewEat/ResInfo/{restaurantId}";
    }


}
