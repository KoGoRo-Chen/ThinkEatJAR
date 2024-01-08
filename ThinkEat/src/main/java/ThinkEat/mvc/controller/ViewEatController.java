package ThinkEat.mvc.controller;

import ThinkEat.mvc.entity.EatRepo;
import ThinkEat.mvc.dao.EatDataDao;
import ThinkEat.mvc.dao.EatRepoDao;
import ThinkEat.mvc.entity.ResData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("ViewEat/")
public class ViewEatController {

    @Autowired
    @Qualifier("shareEatDaoImplInMemory")
    private EatRepoDao eatRepoDao;

    @Autowired
    @Qualifier("eatDataDaoImplInMemory")
    private EatDataDao eatDataDao;

    //顯示ShowEat頁面(顯示所有餐廳)
    @GetMapping("/ShowEat")
    public String GetShowEatPage(Model model){
        List<ResData> resSum = eatRepoDao.findAllres();
        System.out.println(resSum);
        model.addAttribute("resSum", resSum);
        return "ViewEat/ShowEat";
    };

    //顯示ViewEat/Res/{ResId}/頁面
    @GetMapping("/ResInfo/{ResId}")
    public String getResPage(@PathVariable("ResId") Integer ResId,  Model model){
        // 1. 根據 resId 從數據庫中檢索相應的 res
        EatRepo resByResId = eatRepoDao.findresByresID(ResId);
        List<EatRepo> eatsByResId = eatRepoDao.findAlleatByresID(ResId);

        // 2. 將檢索到的 res 及ID添加到模型中
        model.addAttribute("resByResId", resByResId);
        model.addAttribute("eatsByResId", eatsByResId);

        // 返回 ViewEat 頁面
        return "ViewEat/ResInfo";
    };

    //顯示ViewEat/EatRepo/{ShareEatId}頁面
    @GetMapping("/EatRepo/{eatRepoId}")
    public String GetViewEatPage(@PathVariable("eatRepoId") Integer eatRepoId, Model model){
        // 1. 根據 shareEatId 從數據庫中檢索相應的 ShareEatBean
        Optional<EatRepo> eatRepoOptional = eatRepoDao.getEatByShareEatId(eatRepoId);

        // 2. 检查 Optional 是否包含值，如果有，将 EatRepo 对象添加到模型中
        eatRepoOptional.ifPresent(eatRepo -> {
            model.addAttribute("eatRepoId", eatRepo.getEatRepoId());
            model.addAttribute("resId", eatRepo.getResData().getResId());
            model.addAttribute("eatRepo", eatRepo);
            System.out.println("新增成功" + eatRepo);
        });

        // 返回 ViewEat 頁面
        return "ViewEat/EatRepo";
    };


}
