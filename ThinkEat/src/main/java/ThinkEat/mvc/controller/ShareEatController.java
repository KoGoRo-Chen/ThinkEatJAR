package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.*;
import ThinkEat.mvc.model.entity.*;
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
        model.addAttribute("restaurant", new Restaurant());
        List<Restaurant> restaurantList = restaurantService.getAllRestaurant();
        if (restaurantList != null) {
            model.addAttribute("restaurantList", restaurantList);
        } else {
            return "redirect:/ThinkEat/ShareEat/Restaurant/CreateRestaurant";
        }

        return "ShareEat/Restaurant";
    }

    //在餐廳顯示頁面中選擇已經存在的餐廳
    @GetMapping("/Restaurant/{restaurantId}")
    public String PickThisRestaurant(@PathVariable("restaurantId") Integer restaurantId,
                                     Model model) {

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("restaurantId", restaurantId);
        System.out.println("Choose Old Restaurant: " + restaurant);

        List<Restaurant> restaurantList = restaurantService.getAllRestaurant();
        model.addAttribute("restaurantList", restaurantList);
        return "ShareEat/Restaurant";
    }

    // 創建餐廳
    @PostMapping("/CreateRestaurant")
    public String CreateRestaurantPage(Restaurant restaurant,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        Restaurant newRestaurant = new Restaurant();
        model.addAttribute("newRestaurant", newRestaurant);
        newRestaurant.setName(restaurant.getName());
        newRestaurant.setAddress(restaurant.getAddress());

        // 將餐廳保存到資料庫
        Integer id = restaurantService.addRestaurant(newRestaurant);
        restaurant.setId(id);

        // 將新增的餐廳 ID 添加到重定向 URL 的查詢字符串中
        redirectAttributes.addAttribute("restaurantId", id);

        return "redirect:/ThinkEat/ShareEat/Restaurant/{restaurantId}";
    }

    //從餐廳顯示頁面傳導到食記撰寫頁面
    @PostMapping("/ChooseRestaurant")
    public String ChooseRestaurant(@RequestParam("restaurantId") Integer restaurantId,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        System.out.println("restaurantId: " + restaurantId);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurant", restaurant);

        //將新增的餐廳 ID 添加到重定向 URL 的查詢字符串中
        redirectAttributes.addAttribute("restaurantId", restaurantId);

        return "redirect:/ThinkEat/ShareEat/ShareEatRepo/{restaurantId}";
    }

    //顯示食記填寫表單(ShareEat)
    @GetMapping("/ShareEatRepo/{restaurantId}")
    public String GetEatRepoPage(@PathVariable("restaurantId") Integer restaurantId,
                                 Model model) {

        //創建一個新食記，存入餐廳資料並取得ID
        EatRepo eatRepo = new EatRepo();
        model.addAttribute("eatRepo", eatRepo);

        //將餐廳資料存入頁面中
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurant", restaurant);

        //價格
        List<Price> prices = priceService.findAllPrice();
        model.addAttribute("prices", prices);
        Price price = new Price();
        model.addAttribute("price", price);

        //標籤
        List<Tag> tags = tagService.findAllTag();
        model.addAttribute("tags", tags);
        Tag tag = new Tag();
        model.addAttribute("tag", tag);

        return "ShareEat/ShareEatRepo";
    }

    // 將其餘資訊放入同篇食記
    @PostMapping("/AddEatRepo")
    public String addEatRepo(@ModelAttribute("eatRepo") EatRepo eatRepo,
                             @RequestParam("restaurantId") Integer restaurantId,
                             @RequestParam("priceId") Integer priceId,
                             @RequestParam("tagIds") List<Integer> tagIds,
                             @RequestPart("multipartFileList") List<MultipartFile> multipartFileList,
                             HttpSession session,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        System.out.println(eatRepo);

        // 從 Session 中獲取用戶資訊
        User user = (User) session.getAttribute("user");
        System.out.println("get到的user是:" + user);
        eatRepo.setEatRepo_User(user);


        // 根據 restaurantId 獲取相應的 RestaurantDto 對象，然後將其設置到 eatRepoDto 中
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        eatRepo.setRestaurant(restaurant);
        System.out.println("接收到restaurant:" + restaurant);

        //處理價格
        Price price = priceService.getPriceById(priceId);
        eatRepo.setPrice(price);
        //處理標籤
        List<Tag> selectedTags = new ArrayList<>();
        for (Integer tagId : tagIds) {
            Tag fetchedTag = tagService.getTagById(tagId);
            selectedTags.add(fetchedTag);
        }
        eatRepo.setEatRepo_TagList(selectedTags);

        //先儲存食記
        Integer eatRepoId = eatRepoService.addEatRepo(eatRepo);

        //處理多張圖片
        for (MultipartFile multipartFile : multipartFileList) {
            Picture picture = new Picture();
            picture.setPic_EatRepo(eatRepoService.getEatRepoByEatRepoId(eatRepoId));
            picture.setPic_Restaurant(restaurantService.getRestaurantById(restaurantId));
            Integer picDtoId = pictureService.addPicture(picture, multipartFile);
            picture.setId(picDtoId);
            eatRepo.getPicList().add(picture);
        }

        //將圖片放入文章中
        eatRepoService.updateEatRepoByEatRepoId(eatRepoId, eatRepo);
        System.out.println(eatRepo);
        model.addAttribute("eatRepo", eatRepo);

        // 將食記 ID 添加到重定向 URL 的查詢字符串中
        redirectAttributes.addAttribute("eatRepoId", eatRepoId);

        // 重導到文章瀏覽頁面
        return "redirect:/ThinkEat/ViewEat/EatRepo/{eatRepoId}";
    }

    // 增加新價格
    @PostMapping("/CreateNewPrice")
    public String CreateNewPrice(@ModelAttribute("price") Price newPrice,
                                 @RequestParam("restaurantId") Integer restaurantId,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        // 使用新價格的名稱設置PriceDto
        newPrice.setName(newPrice.getName());

        // 將新價格添加到數據庫
        Integer priceId = priceService.addPrice(newPrice);

        redirectAttributes.addAttribute("restaurantId", restaurantId);

        return "redirect:/ThinkEat/ShareEat/ShareEatRepo/{restaurantId}";
    }

    // 增加新TAG
    @PostMapping("/CreateNewTag")
    public String CreateNewTag(@ModelAttribute("tag") Tag newTag,
                               @RequestParam("restaurantId") Integer restaurantId,
                               RedirectAttributes redirectAttributes, Model model) {
        // 將新標籤添加到數據庫
        Integer tagId = tagService.addTag(newTag);

        // 重定向到相應的頁面

        redirectAttributes.addAttribute("restaurantId", restaurantId);

        return "redirect:/ThinkEat/ShareEat/ShareEatRepo/{restaurantId}";
    }



    //顯示食記編輯表單
    @GetMapping("/EditEatRepo/{eatRepoId}")
    public String GetEditEatRepoPage(@PathVariable("eatRepoId") Integer eatRepoId,
                                     Authentication authentication,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {

        //檢查發文者是否具有會員身分
        if (authentication == null || !authentication.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("NotLoginErrorMessage", "請登入後再發表留言。");
            return "redirect:/ThinkEat/Login";
        }

        EatRepo eatRepo = eatRepoService.getEatRepoByEatRepoId(eatRepoId);
        model.addAttribute("eatRepo", eatRepo);
        model.addAttribute("eatRepoId", eatRepoId);

        Restaurant restaurant = eatRepoService.getEatRepoByEatRepoId(eatRepoId).getRestaurant();
        model.addAttribute("restaurant", restaurant);

        //圖片
        if (eatRepo != null && eatRepo.getPicList() != null && !eatRepo.getPicList().isEmpty()) {
            Picture picture = eatRepo.getPicList().get(0);
            model.addAttribute("picture", picture);
        }

        //價格
        List<Price> prices = priceService.findAllPrice();
        model.addAttribute("prices", prices);
        Price price = new Price();
        model.addAttribute("price", price);

        //標籤
        List<Tag> tags = tagService.findAllTag();
        model.addAttribute("tags", tags);
        Tag tag = new Tag();
        model.addAttribute("tag", tag);

        return "ShareEat/Edit";
    }

    //編輯食記
    @PostMapping("/UpdateEatRepo")
    public String updateEatRepo(@RequestParam("eatRepoId") Integer eatRepoId,
                                @RequestParam("restaurantId") Integer restaurantId,
                                @RequestParam("priceId") Integer priceId,
                                @RequestParam("tagIds") List<Integer> tagIds,
                                @RequestPart("multipartFileList") List<MultipartFile> multipartFileList,
                                HttpSession session,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        EatRepo eatRepoToUpdate = eatRepoService.getEatRepoByEatRepoId(eatRepoId);

        // 從 Session 中獲取用戶資訊
        User user = (User) session.getAttribute("user");
        System.out.println("get到的user是:" + user);
        eatRepoToUpdate.setEatRepo_User(user);

        // 根據 restaurantId 獲取相應的 RestaurantDto 對象，然後將其設置到 eatRepoDto 中
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        eatRepoToUpdate.setRestaurant(restaurant);

        //處理價格
        Price price = priceService.getPriceById(priceId);
        eatRepoToUpdate.setPrice(price);

        //處理標籤
        List<Tag> selectedTags = new ArrayList<>();
        for (Integer tagId : tagIds) {
            Tag fetchedTag = tagService.getTagById(tagId);
            selectedTags.add(fetchedTag);
        }
        eatRepoToUpdate.setEatRepo_TagList(selectedTags);

        //處理多張圖片
        for (MultipartFile multipartFile : multipartFileList) {
            Picture picture = new Picture();
            picture.setPic_EatRepo(eatRepoToUpdate);
            picture.setPic_Restaurant(restaurant);
            Integer picDtoId = pictureService.addPicture(picture, multipartFile);
            picture.setId(picDtoId);
            eatRepoToUpdate.getPicList().add(picture);
        }

        //將圖片放入文章中
        eatRepoService.updateEatRepoByEatRepoId(eatRepoId, eatRepoToUpdate);
        System.out.println(eatRepoToUpdate);

        // 將食記 ID 添加到重定向 URL 的查詢字符串中
        redirectAttributes.addAttribute("eatRepoId", eatRepoId);

        // 重導到文章瀏覽頁面
        return "redirect:/ThinkEat/ViewEat/EatRepo/{eatRepoId}";
    }


    //刪除文章
    @PostMapping("/ShareEatRepo/Delete/{eatRepoId}")
    public String deleteEatRepo(@PathVariable("eatRepoId") Integer eatRepoId,
                                RedirectAttributes redirectAttributes) {
        //以ID找到正確的eatRepo
        EatRepo eatRepo = eatRepoService.getEatRepoByEatRepoId(eatRepoId);

        //找到對應的餐廳，並把餐廳ID加入重新導向功能
        Restaurant restaurant = eatRepo.getRestaurant();
        Integer restaurantId = restaurant.getId();
        redirectAttributes.addAttribute("restaurantId", restaurantId);

        //service執行刪除
        eatRepoService.deleteEatRepo(eatRepoId);

        return "redirect:/ThinkEat/ViewEat/ResInfo/{restaurantId}";
    }

    //刪除文章中的照片
    @PostMapping("/DeletePicture")
    public String deletePicture(@RequestParam("eatRepoId") Integer eatRepoId,
                                @RequestParam("pictureId") Integer pictureId,
                                RedirectAttributes redirectAttributes) {

        pictureService.deletePicture(pictureId);
        redirectAttributes.addAttribute("eatRepoId", eatRepoId);

        return "redirect:/ThinkEat/ShareEat/EditEatRepo/{eatRepoId}";
    }

}
