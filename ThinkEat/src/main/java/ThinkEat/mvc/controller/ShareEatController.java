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

@Controller
@RequestMapping("/")
public class ShareEatController {

    @Autowired
    @Qualifier("shareEatDaoImplInMemory")
    private ShareEatDao shareEatDao;

    @Autowired
    @Qualifier("eatDataDaoImplInMemory")
    private EatDataDao eatDataDao;

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

    //新增ShareEatBean(食記)
    @PostMapping("/AddEatRepo")
    public String AddEatRepo(@ModelAttribute("eatRepo") EatRepo eatRepo,
                             //@RequestParam("eatPic") MultipartFile eatPic,
                             RedirectAttributes redirectAttributes, Model model) {
        List<PriceData> prices = eatDataDao.findAllPriceDatas();
        List<TagData> tags = eatDataDao.findAllTagDatas();

        // 表單新增過程中只有帶到ID沒有帶到name，故需要額外處理
        // 處理 price (從 priceId -> price)
        PriceData priceData = eatDataDao.getPriceDataById(eatRepo.getPriceId()).get();
        eatRepo.setPrice(priceData);

        // 處理 tags (從 tagId -> tags)
        List<TagData> tagDatas = new ArrayList<>();
        for(Integer tagId: eatRepo.getTagIds()) {
            TagData tagData = eatDataDao.getTagDataById(tagId).get();
            tagDatas.add(tagData);
            ;		}
        eatRepo.setTags(tagDatas);

		 /* 處理圖片上傳
	    if (!eatPic.isEmpty()) {
	        try {
	            // 可以使用檔案工具類處理檔案，例如 Apache Commons IO
	            byte[] eatPicBytes = eatPic.getBytes();
	            // 這裡你可以將 eatPicBytes 存儲到資料庫或檔案系統中
	            String base64Image = Base64.getEncoder().encodeToString(eatPicBytes);

	            // 更新 shareEatBean 中的圖片屬性
	            shareEatBean.setEatPicBytes(eatPicBytes);
	            shareEatBean.setEatPicBase64(base64Image);
	        } catch (IOException e) {
	            e.printStackTrace(); // 適當的錯誤處理
	        }
	    }*/

        int rowcount = shareEatDao.addEat(eatRepo);
        System.out.println(eatRepo);
        System.out.println("add ShareEat rowcount = " + rowcount);


        // 將 ShareEatBean 添加到模型中
        model.addAttribute("eatRepo", eatRepo);
        model.addAttribute("prices", prices);
        model.addAttribute("tags",  tags);

        // 將新建的食記 ID 添加到重定向 URL 的查詢字符串中
        redirectAttributes.addAttribute("eatRepoId", eatRepo.getEatRepoId());

        return "redirect:/ThinkEat/ViewEat/EatRepo/{eatRepoId}"; // 重導到文章瀏覽頁面
    }
}
