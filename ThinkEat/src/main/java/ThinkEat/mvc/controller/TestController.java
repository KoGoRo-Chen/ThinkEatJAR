package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.PictureDto;
import ThinkEat.mvc.service.PictureService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("Test/")
public class TestController {
    private final PictureService pictureService;

    @Autowired
    public TestController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @Value("${spring.mvc.servlet.path:/}") // 如果屬性不存在，默認為 "/"
    private String servletPath;


    //顯示上傳圖片頁面
    @GetMapping("/PicUploadTest")
    public String GetPicUploadTestPage(Model model) throws IOException {

        return "Test/PicUploadTest";
    }

    //處理圖片上傳
    @PostMapping("/Upload")
    public String uploadPictures(@RequestPart("pictures") List<MultipartFile> multipartFileList,
                                 Model model) {
        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList) {
            PictureDto pictureDto = new PictureDto();
            Integer picId = pictureService.addPicture(pictureDto, multipartFile);
            imagePaths.add(pictureService.getPictureById(picId).getPath());
        }
        model.addAttribute("imagePaths", imagePaths);
        return "Test/PicUploadTest";
    }


}

