package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.PictureDto;
import ThinkEat.mvc.service.PictureService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("Test/")
public class TestController {
    private final PictureService pictureService;

    @Autowired
    public TestController(PictureService pictureService) {
        this.pictureService = pictureService;
    }


    //顯示上傳圖片頁面
    @GetMapping("/PicUploadTest")
    public String GetPicUploadTestPage(Model model) {
        PictureDto pictureDto = new PictureDto();
        model.addAttribute("pictureDto", pictureDto);
        return "Test/PicUploadTest";
    }

    //處理圖片上傳
    @PostMapping("/Upload")
    public String UploadPicture(@RequestPart("picture") MultipartFile multipartFile,
                                @ModelAttribute("pictureDto") PictureDto pictureDto,
                                HttpServletResponse response,
                                Model model) {
        Integer picId = pictureService.addPicture(pictureDto, multipartFile);
        System.out.println(pictureService.getPictureById(picId).getPath());
        model.addAttribute("pictureDto", pictureService.getPictureById(picId));
        return "Test/PicUploadTest";
    }
}
