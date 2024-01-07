package ThinkEat.mvc.controller;

import ThinkEat.mvc.bean.EatRepo;
import ThinkEat.mvc.bean.PriceData;
import ThinkEat.mvc.bean.ResData;
import ThinkEat.mvc.bean.TagData;
import ThinkEat.mvc.dao.EatDataDao;
import ThinkEat.mvc.dao.ShareEatDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ShareEatController {

    private final EatDataDao eatDataDao;
    private final ShareEatDao shareEatDao;

    // 使用建構子注入EatDataDao和ShareEatDao
    @Autowired
    public ShareEatController(EatDataDao eatDataDao, ShareEatDao shareEatDao) {
        this.eatDataDao = eatDataDao;
        this.shareEatDao = shareEatDao;
    }

    //顯示ShareEat頁面
    @GetMapping("/ShareEat")
    public String GetShareEatPage(Model model){
        List<PriceData> prices = eatDataDao.findAllPriceDatas();
        List<TagData> tags = eatDataDao.findAllTagDatas();
        model.addAttribute("prices", prices);
        model.addAttribute("tags", tags);
        model.addAttribute("eatRepo", new EatRepo());
        return "ShareEat";
    };

    // 表單提交處理
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
        int rowcount = shareEatDao.addEat(eatRepo);
        System.out.println(eatRepo);
        System.out.println("Add ShareEat rowcount = " + rowcount);

        // 將新增的食記 ID 添加到重定向 URL 的查詢字符串中
        redirectAttributes.addAttribute("eatRepoId", eatRepo.getEatRepoId());

        // 重導到文章瀏覽頁面
        return "redirect:/ThinkEat/ViewEat/EatRepo/{eatRepoId}";
    }
}
