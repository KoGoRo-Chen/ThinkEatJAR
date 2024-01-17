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
    public String UploadPicture(@RequestPart("picture") MultipartFile multipartFile,
                                HttpServletResponse response,
                                Model model) {
        PictureDto pictureDto = new PictureDto();
        Integer picId = pictureService.addPicture(pictureDto, multipartFile);
        model.addAttribute("pictureDto", pictureDto);
        System.out.println(pictureService.getPictureById(picId).getPath());
        model.addAttribute("imagePath", pictureService.getPictureById(picId).getPath());
        return "Test/PicUploadTest";
    }

    @GetMapping("/image")
    public String getImage(Model model) {
        String imagePath = servletPath + "1705405519228_OIP.jpg"; // 靜態資源的相對路徑
        model.addAttribute("imagePath", imagePath);
        // Thymeleaf的頁面名稱
        return "Test/PicUploadTest";
    }


//    //顯示圖片
//    @GetMapping("/Get")
//    @ResponseBody
//    public ResponseEntity<byte[]> getImage(@RequestParam String fileName) throws IOException {
//        Path imagePath = Paths.get(IMAGE_FOLDER, fileName);
//
//        // 讀取圖片為byte陣列
//        byte[] imageBytes = Files.readAllBytes(imagePath);
//
//        // 將byte陣列轉換為BufferedImage
//        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
//
//        // 處理BufferedImage（這裡可以添加您的圖片處理邏輯）
//
//        // 將處理後的BufferedImage轉換為byte陣列
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
//        byte[] processedImageBytes = byteArrayOutputStream.toByteArray();
//
//        // 設定HTTP頭部，告訴瀏覽器圖片的MIME類型
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//
//        // 返回ResponseEntity，包含圖片資料和HTTP頭部
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentLength(processedImageBytes.length)
//                .body(processedImageBytes);
//    }
}

