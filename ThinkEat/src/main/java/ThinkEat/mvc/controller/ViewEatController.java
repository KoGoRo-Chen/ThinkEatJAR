package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.*;
import ThinkEat.mvc.model.entity.FavList;
import ThinkEat.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("ViewEat/")
public class ViewEatController {

    private final FavListService favListService;
    private final PriceService priceService;
    private final TagService tagService;
    private final EatRepoService eatRepoService;
    private final CommentService commentService;
    private final RestaurantService restaurantService;
    private final PictureService pictureService;

    @Autowired
    public ViewEatController(FavListService favListService, PriceService priceService,
                             TagService tagService,
                             EatRepoService eatRepoService,
                             CommentService commentService,
                             RestaurantService restaurantService,
                             PictureService pictureService) {
        this.favListService = favListService;
        this.priceService = priceService;
        this.tagService = tagService;
        this.eatRepoService = eatRepoService;
        this.commentService = commentService;
        this.restaurantService = restaurantService;
        this.pictureService = pictureService;
    }

    //顯示ShowEat頁面(顯示所有餐廳)
    @GetMapping("/ShowEat/")
    public String GetShowEatPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "12") int size,
                                 @ModelAttribute RestaurantDto restaurantDto,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, size);
        ShowEatPageDto showEatPageDto = restaurantService.getAllRestaurant(pageable);
        model.addAttribute("showEatPageDto", showEatPageDto);

        Integer maxPage = showEatPageDto.getTotalPage();
        model.addAttribute("maxPage", maxPage);

        Integer curPage = showEatPageDto.getCurrentPage();
        model.addAttribute("curPage", curPage);

        return "ViewEat/ShowEat";
    }

    //顯示ViewEat//ResInfo/{restaurantId}/頁面
    @GetMapping("/ResInfo/{restaurantId}")
    public String getResPage(@PathVariable("restaurantId") Integer restaurantId,
                             Model model) {
        // 尋找餐廳餐廳
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurantDto", restaurantDto);

        // 尋找這間餐廳的所有食記
        List<EatRepoDto> eatRepoDtoList = restaurantService.getAllEatRepoByRestaurantId(restaurantId);
        model.addAttribute("eatRepoDto", eatRepoDtoList);

        Integer eatRepoListCount = eatRepoDtoList.size();
        model.addAttribute("eatRepoListCount", eatRepoListCount);


        // 取得屬於這間餐廳的所有食記的照片的網址
        List<PictureDto> pictureDtoList = restaurantService.getAllPictureByRestaurantId(restaurantId);
        List<String> restaurantImagePathList = new ArrayList<>();
        for (PictureDto pictureDto : pictureDtoList) {
            restaurantImagePathList.add(pictureDto.getHtmlPath());
        }
        model.addAttribute("restaurantImagePathList", restaurantImagePathList);

        //處理價位
        List<Integer> allPriceIdList = new ArrayList<>();
        for (EatRepoDto eatRepoDto : eatRepoDtoList) {
            Integer priceId = eatRepoDto.getPriceId();
            allPriceIdList.add(priceId);
        }
        System.out.println("目前收集到的priceId有：" + allPriceIdList);

        double averagePriceId = allPriceIdList.stream()
                .mapToDouble(Integer::doubleValue) // 將Integer轉換成double
                .average()                          // 計算平均值
                .orElse(0.0);                       // 如果沒有元素，預設為0.0


        // 將double轉換為Integer
        Integer roundedAveragePriceId = Integer.valueOf((int) Math.round(averagePriceId));
        System.out.println("收集到的priceId的平均值為：" + roundedAveragePriceId);
        String averagePrice = priceService.getPriceById(roundedAveragePriceId).getName();
        model.addAttribute("averagePrice", averagePrice);

        //處理標籤
        List<TagDto> allTagDtoList = new ArrayList<>();
        for (EatRepoDto eatRepoDto : eatRepoDtoList) {
            for (TagDto tagDto : eatRepoDto.getEatRepo_TagList()) {
                allTagDtoList.add(tagDto);
            }
        }

        // 取得所有標籤名稱的List
        List<String> allTagNames = allTagDtoList.stream()
                .map(TagDto::getName)
                .collect(Collectors.toList());
        System.out.println("所有標籤名稱：" + allTagNames);

        // 使用 Map 來計算每個標籤的出現次數
        Map<String, Long> tagFrequencyMap = allTagNames.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // 排序 Map，取出現次數最多的五個標籤的 name 屬性
        List<String> topFiveTagNameList = tagFrequencyMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)  // 取得 name 屬性
                .collect(Collectors.toList());

        System.out.println("出現次數前五多的標籤：" + topFiveTagNameList);
        model.addAttribute("topFiveTagNameList", topFiveTagNameList);

        // 返回 ViewEat 頁面
        return "ViewEat/ResInfo";
    }

    //刪除餐廳
    @PostMapping("/ResInfo/DeleteRestaurant")
    public String DeleteRestaurant(@RequestParam("restaurantId") Integer restaurantId,
                                   Model model) {
        // 根據 restaurantId 從數據庫中檢索相應的 餐廳
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);

        //RestaurantService執行刪除
        restaurantService.deleteRestaurant(restaurantId);

        // 返回 ShowEat 頁面
        return "redirect:/ThinkEat/ViewEat/ShowEat";
    }

    //顯示ViewEat/EatRepo/{eatRepoId}頁面
    @GetMapping("/EatRepo/{eatRepoId}")
    public String GetViewEatPage(@PathVariable("eatRepoId") Integer eatRepoId, Model model) {
        // 根據 eatRepoId 從數據庫中檢索相應的 食記
        EatRepoDto eatRepoDto = eatRepoService.getEatRepoByEatRepoId(eatRepoId);
        System.out.println("ViewEat頁面顯示eatRepoDto: " + eatRepoDto);
        model.addAttribute("eatRepoDto", eatRepoDto);
        System.out.println("新增成功" + eatRepoDto);
        model.addAttribute("restaurantId", eatRepoDto.getRestaurant().getId());
        model.addAttribute("eatRepoId", eatRepoDto.getId());

        //取得圖片網址並加入清單中
        List<PictureDto> pictureDtoList = eatRepoDto.getPicList();
        List<String> imagePaths = new ArrayList<>();
        for(PictureDto pictureDto : pictureDtoList){
            imagePaths.add(pictureDto.getHtmlPath());
        }
        model.addAttribute("imagePaths", imagePaths);

        //加入收藏清單
        model.addAttribute("allFavList", favListService.findAllFavList());

        //處理留言
        List<CommentDto> commentDtoList = eatRepoService.findAllCommentByEatRepoId(eatRepoId);
        model.addAttribute("commentDtoList", commentDtoList);
        model.addAttribute("commentDto", new CommentDto());

        // 返回 ViewEat 頁面
        return "ViewEat/EatRepo";
    }

    //發表留言
    @PostMapping("/EatRepo/PostComment")
    public String PostComment(@ModelAttribute("commentDto") CommentDto commentDto,
                              @RequestParam("eatRepoId") Integer eatRepoId,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        //儲存留言
        commentDto.setComment_EatRepo(eatRepoService.getEatRepoByEatRepoId(eatRepoId));
        commentService.addComment(commentDto);
        System.out.println("新增留言成功: " + commentDto);

        //重導回食記瀏覽頁面
        redirectAttributes.addAttribute("eatRepoId", eatRepoId);

        // 返回 ViewEat 頁面
        return "redirect:/ThinkEat/ViewEat/EatRepo/{eatRepoId}";
    }

    //刪除留言
    @PostMapping("/EatRepo/DeleteComment")
    public String DeleteComment(@RequestParam("commentId") Integer commentId,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        //找到留言
        CommentDto commentDto = commentService.getCommentById(commentId);

        //找到所屬食記ID並重導回食記瀏覽頁面
        Integer eatRepoId = commentDto.getComment_EatRepo().getId();
        redirectAttributes.addAttribute("eatRepoId", eatRepoId);

        //執行刪除
        commentService.deleteCommentById(commentId);

        // 返回 ViewEat 頁面
        return "redirect:/ThinkEat/ViewEat/EatRepo/{eatRepoId}";
    }


}
