package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.CommentDto;
import ThinkEat.mvc.model.dto.EatRepoDto;
import ThinkEat.mvc.model.dto.PictureDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.model.entity.FavList;
import ThinkEat.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

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
    @GetMapping("/ShowEat")
    public String GetShowEatPage(Model model) {
        List<RestaurantDto> restaurantDtoList = restaurantService.getAllRestaurant();
        model.addAttribute("restaurantDtoList", restaurantDtoList);
        return "ViewEat/ShowEat";
    }

    //顯示ViewEat//ResInfo/{restaurantId}/頁面
    @GetMapping("/ResInfo/{restaurantId}")
    public String getResPage(@PathVariable("restaurantId") Integer restaurantId,
                             Model model) {
        // 根據 restaurantId 從數據庫中檢索相應的 餐廳
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(restaurantId);
        List<EatRepoDto> eatRepoDto = restaurantService.getAllEatRepoByRestaurantId(restaurantId);

        // 取得屬於這間餐廳的所有食記的照片
        List<PictureDto> pictureDtoList = restaurantService.getAllPictureByRestaurantId(restaurantId);
        List<String> restaurantImagePathList = new ArrayList<>();
        for (PictureDto pictureDto : pictureDtoList) {
            restaurantImagePathList.add(pictureDto.getHtmlPath());
        }
        model.addAttribute("restaurantImagePathList", restaurantImagePathList);

        // 將檢索到的 餐廳 及 擁有的食記 添加到模型中
        model.addAttribute("restaurantDto", restaurantDto);
        model.addAttribute("eatRepoDto", eatRepoDto);

        // 返回 ViewEat 頁面
        return "ViewEat/ResInfo";
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


}
