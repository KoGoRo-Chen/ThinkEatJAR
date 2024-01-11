package ThinkEat.mvc.controller;

import ThinkEat.mvc.dto.EatRepoDto;
import ThinkEat.mvc.dto.PriceDataDto;
import ThinkEat.mvc.dto.ResDataDto;
import ThinkEat.mvc.dto.TagDataDto;
import ThinkEat.mvc.service.EatRepoService;
import ThinkEat.mvc.service.PriceDataService;
import ThinkEat.mvc.service.ResDataService;
import ThinkEat.mvc.service.TagDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("ShareEat/")
public class ShareEatController {

    private final PriceDataService priceDataService;
    private final TagDataService tagDataService;
    private final EatRepoService eatRepoService;
    private final ResDataService resDataService;

    // 使用建構子注入EatDataDao、ResDataDao、ShareEatDao
    @Autowired
    public ShareEatController(PriceDataService priceDataService, TagDataService tagDataService, EatRepoService eatRepoService, ResDataService resDataService) {
        this.priceDataService = priceDataService;
        this.tagDataService = tagDataService;
        this.eatRepoService = eatRepoService;
        this.resDataService = resDataService;
    }

    //顯示餐廳建立表單
    @GetMapping("/ShareResData")
    public String GetShareResPage(Model model){
        List<ResDataDto> resSum = resDataService.getAllResData();
        model.addAttribute("resSum", resSum);
        model.addAttribute("resDataDto", new ResDataDto());
        return "ShareEat/ShareResData";
    };

    // 創建餐廳
    @PostMapping("/AddResData")
    public String addResData(@ModelAttribute("resDataDto") ResDataDto resDataDto,
                             RedirectAttributes redirectAttributes, Model model) {
        // 將餐廳保存到資料庫
        int rowcount = resDataService.addResData(resDataDto);
        System.out.println(resDataDto);
        System.out.println("ResDataDto Added, resDtoId= " + resDataDto.getResId());
        model.addAttribute("resDataDto", resDataDto);

        // 將新增的餐廳 ID 添加到重定向 URL 的查詢字符串中
        redirectAttributes.addAttribute("resId", resDataDto.getResId());

        // 重導到食記填寫表單(ShareEat)
        return "redirect:/ThinkEat/ShareEat/ShareEatRepo/{resId}";
    }

    //顯示食記填寫表單(ShareEat)
    @GetMapping("/ShareEatRepo/{resId}")
    public String GetShareEatPage(@PathVariable("resId") Integer resId, Model model){
        ResDataDto resDataDto = resDataService.getResDataById(resId);
        model.addAttribute("resDataDto", resDataDto);

        List<PriceDataDto> prices = priceDataService.findAllPrice();
        model.addAttribute("prices", prices);
        List<TagDataDto> tags = tagDataService.findAllTag();
        model.addAttribute("tags", tags);

        EatRepoDto eatRepoDto = new EatRepoDto(); // 创建 eatRepoDto 对象
        model.addAttribute("eatRepoDto", eatRepoDto); // 将 eatRepoDto 放入模型中

        return "ShareEat/ShareEatRepo";
    };

    // 創建食記
    @PostMapping("/AddEatRepo")
    public String addEatRepo(@ModelAttribute("eatRepoDto") EatRepoDto eatRepoDto,
                             @RequestParam("resId") Integer resId,
                             RedirectAttributes redirectAttributes, Model model) {

        // 根據 resDataDtoId 獲取相應的 ResDataDto 對象，然後將其設置到 eatRepoDto 中
        ResDataDto resDataDto = resDataService.getResDataById(resId);
        eatRepoDto.setResDataDto(resDataDto);
        System.out.println("接收到resDataDto:" +resDataDto);

        // 處理價格
        Optional<PriceDataDto> priceDataDtoOpt = priceDataService.getPriceById(eatRepoDto.getPriceDtoId());
        if (priceDataDtoOpt.isPresent()) {
            // 創建價格對象並將其設置到 eatRepoDto 中
            PriceDataDto price = priceDataDtoOpt.get();
            eatRepoDto.setPriceDataDto(price);
        }

        //處理標籤
        List<TagDataDto> selectedTagDtos = new ArrayList<>();
        for (Integer tagId : eatRepoDto.getTagDtoIds()) {
            Optional<TagDataDto> tagDataDtoOpt = tagDataService.getTagById(tagId);
            tagDataDtoOpt.ifPresent(selectedTagDtos::add);
        }
        eatRepoDto.setTagDataDtos(selectedTagDtos);

        // 將食記保存到資料庫
        int rowcount = eatRepoService.addEatRepo(eatRepoDto); // 使用 eatRepoService
        System.out.println(eatRepoDto);
        System.out.println("Add ShareEat rowcount = " + rowcount);
        model.addAttribute("eatRepoDto", eatRepoDto);


        // 將新增的食記 ID 添加到重定向 URL 的查詢字符串中
        redirectAttributes.addAttribute("eatRepoDtoId", eatRepoDto.getEatRepoDtoId());

        // 重導到文章瀏覽頁面
        return "redirect:/ThinkEat/ViewEat/EatRepo/{eatRepoDtoId}";
    }
}
