package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.PictureDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/")
public class ThinkEatController {
    private final EatRepoService eatRepoService;
    private final RestaurantService restaurantService;
    private final PriceService priceService;
    private final TagService tagService;
    private final PictureService pictureService;

    @Autowired
    public ThinkEatController(EatRepoService eatRepoService,
                              RestaurantService restaurantService,
                              PriceService priceService,
                              TagService tagService,
                              PictureService pictureService) {
        this.eatRepoService = eatRepoService;
        this.restaurantService = restaurantService;
        this.priceService = priceService;
        this.tagService = tagService;
        this.pictureService = pictureService;
    }

    //顯示首頁
    @GetMapping("/Index")
    public String GetIndexPage(Model model) {

        //挑出所有餐廳
        List<RestaurantDto> restaurantDtoList = restaurantService.getAllRestaurant();

//        //取得挑出的餐廳的所有圖片
//        List<String> htmlPathList = new ArrayList<>();
//        for (RestaurantDto restaurantDto : restaurantDtoList){
//            Optional<PictureDto> pictureDto = restaurantDto.getResPicList().stream().findFirst();
//            if(pictureDto.isPresent()){
//                htmlPathList.add(pictureDto.get().getHtmlPath());
//            }
//        }
//
//        //取得挑出的餐廳的所有店名
//        List<String> restaurantNameList = new ArrayList<>();
//        for (RestaurantDto restaurantDto : restaurantDtoList){
//            restaurantNameList.add(restaurantDto.getName());
//        }

        model.addAttribute("restaurantDtoList", restaurantDtoList);
//        model.addAttribute("htmlPathList", htmlPathList);
//        model.addAttribute("restaurantNameList", restaurantNameList);

        return "Index";
    }
}


