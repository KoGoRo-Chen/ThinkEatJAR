package ThinkEat.mvc.controller;

import ThinkEat.mvc.dao.ResDataDao;
import ThinkEat.mvc.entity.EatRepo;
import ThinkEat.mvc.entity.PriceData;
import ThinkEat.mvc.entity.ResData;
import ThinkEat.mvc.entity.TagData;
import ThinkEat.mvc.dao.EatDataDao;
import ThinkEat.mvc.dao.EatRepoDao;
import ThinkEat.mvc.entity.dto.ResDataDto;
import ThinkEat.mvc.service.ResDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ShareEat")
public class ShareEatController {

    private final EatDataDao eatDataDao;
    private final EatRepoDao eatRepoDao;
    private final ResDataService resDataService;

    // 使用建構子注入EatDataDao、ResDataDao、ShareEatDao
    @Autowired
    public ShareEatController(EatDataDao eatDataDao, EatRepoDao eatRepoDao, ResDataService resDataService) {
        this.eatDataDao = eatDataDao;
        this.eatRepoDao = eatRepoDao;
        this.resDataService = resDataService;
    }

    //顯示餐廳建立表單
    @GetMapping("/ShareRes")
    public String GetShareResPage(Model model){
        List<ResData> resSum = resDataService.getAllResData();
        model.addAttribute("resSum", resSum);
        model.addAttribute("resData", new ResData());
        return "ShareRes";
    };

    // 創建餐廳
    @PostMapping("/AddResData")
    public String addResData(@ModelAttribute("resDataDto") ResDataDto resDataDto,
                             RedirectAttributes redirectAttributes, Model model) {
        // 將食記保存到資料庫
        int rowcount = resDataService.addResData(resDataDto);
        System.out.println(resData);
        System.out.println("Add ShareEat rowcount = " + rowcount);
        model.addAttribute("resData", resData);

        // 將新增的食記 ID 添加到重定向 URL 的查詢字符串中
        redirectAttributes.addAttribute("resId", resData.getResId());

        // 重導到食記填寫表單(ShareEat)
        return "redirect:/ThinkEat/ShareEat/{resId}";
    }

    //顯示食記填寫表單(ShareEat)
    @GetMapping("/ShareEat/{resId}")
    public String GetShareEatPage(@PathVariable("resId") Integer resId, Model model){
        List<PriceData> prices = eatDataDao.findAllPriceDatas();
        List<TagData> tags = eatDataDao.findAllTagDatas();
        ResData resData = resDataService.getResData(resId).get();

        model.addAttribute("resData", resData);
        model.addAttribute("prices", prices);
        model.addAttribute("tags", tags);
        model.addAttribute("eatRepo", new EatRepo());
        return "ShareEat";
    };

    // 創建食記
    @PostMapping("/AddEatRepo")
    public String addEatRepo(@ModelAttribute("eatRepo") EatRepo eatRepo,
                             //@RequestParam("eatPic") MultipartFile eatPic,
                             RedirectAttributes redirectAttributes, Model model) {
        System.out.println("接收到priceID為" + eatRepo.getPriceId());
        System.out.println("接收到tagID為"+ eatRepo.getTagIds());

        // 處理價格和標籤
        Optional<PriceData> priceData = eatDataDao.getPriceDataById(eatRepo.getPriceId());
        if (priceData.isPresent()) {
            PriceData price = priceData.get();
            eatRepo.setPrice(price);
        }

        List<TagData> selectedTags = new ArrayList<>();
        for (Integer tagId : eatRepo.getTagIds()) {
            Optional<TagData> tagDataOptional = eatDataDao.getTagDataById(tagId);
            tagDataOptional.ifPresent(selectedTags::add);
        }
        eatRepo.setTags(selectedTags);

        /*
        // 處理圖片上傳
        if (!eatPic.isEmpty()) {
            try {
                byte[] eatPicBytes = eatPic.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(eatPicBytes);

                eatRepo.setEatPicBytes(eatPicBytes);
                eatRepo.setEatPicBase64(base64Image);
            } catch (IOException e) {
                e.printStackTrace(); // 適當的錯誤處理
            }
        }
         */

        // 將食記保存到資料庫
        int rowcount = eatRepoDao.addEat(eatRepo);
        System.out.println(eatRepo);
        System.out.println("Add ShareEat rowcount = " + rowcount);
        model.addAttribute("eatRepo", eatRepo);

        // 將新增的食記 ID 添加到重定向 URL 的查詢字符串中
        redirectAttributes.addAttribute("eatRepoId", eatRepo.getEatRepoId());

        // 重導到文章瀏覽頁面
        return "redirect:/ThinkEat/ViewEat/EatRepo/{eatRepoId}";
    }
}
