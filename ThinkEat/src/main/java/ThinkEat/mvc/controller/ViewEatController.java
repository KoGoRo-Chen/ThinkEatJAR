package ThinkEat.mvc.controller;

import ThinkEat.mvc.entity.dto.EatRepoDto;
import ThinkEat.mvc.entity.dto.ResDataDto;
import ThinkEat.mvc.service.EatRepoService;
import ThinkEat.mvc.service.PriceDataService;
import ThinkEat.mvc.service.ResDataService;
import ThinkEat.mvc.service.TagDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("ViewEat/")
public class ViewEatController {

    private final PriceDataService priceDataService;
    private final TagDataService tagDataService;
    private final EatRepoService eatRepoService;
    private final ResDataService resDataService;

    @Autowired
    public ViewEatController(PriceDataService priceDataService,
                             TagDataService tagDataService,
                             EatRepoService eatRepoService,
                             ResDataService resDataService) {
        this.priceDataService = priceDataService;
        this.tagDataService = tagDataService;
        this.eatRepoService = eatRepoService;
        this.resDataService = resDataService;
    }

    //顯示ShowEat頁面(顯示所有餐廳)
    @GetMapping("/ShowEat")
    public String GetShowEatPage(Model model){
        List<ResDataDto> resDtoSum = resDataService.getAllResData();
        System.out.println(resDtoSum);
        model.addAttribute("resDtoSum", resDtoSum);
        return "ViewEat/ShowEat";
    };

    //顯示ViewEat/Res/{ResId}/頁面
    @GetMapping("/ResInfo/{resId}")
    public String getResPage(@PathVariable("resId") Integer resId, Model model) {
        // 1. 根據 resId 從數據庫中檢索相應的 res
        ResDataDto resDtoByResId = resDataService.getResDataById(resId);
        List<EatRepoDto> eatsDtoByResId = resDataService.getAllEatRepoByResId(resId);

        // 2. 將檢索到的 res 及ID添加到模型中
        model.addAttribute("resByResId", resDtoByResId);
        model.addAttribute("eatsByResId", eatsDtoByResId);

        // 返回 ViewEat 頁面
        return "ViewEat/ResInfo";
    };

    //顯示ViewEat/EatRepo/{eatRepoId}頁面
    @GetMapping("/EatRepo/{eatRepoId}")
    public String GetViewEatPage(@PathVariable("eatRepoId") Integer eatRepoId, Model model){
        // 1. 根據 shareEatId 從數據庫中檢索相應的 ShareEatBean
        EatRepoDto eatRepoDto = eatRepoService.getEatRepoByEatRepoId(eatRepoId);

        // 2. 检查 Optional 是否包含值，如果有，将 EatRepo 对象添加到模型中
        model.addAttribute("eatRepoId", eatRepoDto.getEatRepoDtoId());
        model.addAttribute("resId", eatRepoDto.getResDataDto().getResDtoId());
        model.addAttribute("eatRepo", eatRepoDto);
        System.out.println("新增成功" + eatRepoDto);


        // 返回 ViewEat 頁面
        return "ViewEat/EatRepo";
    };


}
