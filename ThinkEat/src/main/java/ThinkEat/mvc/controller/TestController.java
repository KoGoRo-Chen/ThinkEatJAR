package ThinkEat.mvc.controller;

import ThinkEat.mvc.model.dto.PictureDto;
import ThinkEat.mvc.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                                PictureDto pictureDto,
                                RedirectAttributes redirectAttributes) {
        try {
            // 檔案上傳路徑，這裡設定為當前專案的根目錄下的 "uploads" 資料夾
            Resource resource = new ClassPathResource("static/images");
            String uploadPath = resource.getFile().getAbsolutePath();

            // 確認目錄存在，如果不存在就建立目錄
            File directory = new File(uploadPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 取得檔案原始名稱
            String originalFileName = multipartFile.getOriginalFilename();

            // 儲存檔案
            File saveFile = new File(uploadPath + originalFileName);
            multipartFile.transferTo(saveFile);

            // 建立圖片的Path
            String imagePath = "/uploads/" + originalFileName;

            // 建立 PictureDto
            pictureDto.setPath(imagePath);
            pictureService.addPicture(pictureDto);

            //導回上傳頁面
            System.out.println("File uploaded successfully!");
            redirectAttributes.addAttribute("pictureDto", pictureDto);
            return "redirect:/ThinkEat/Test/PicUploadTest";

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("File upload failed.", e);
        }

    }
}
