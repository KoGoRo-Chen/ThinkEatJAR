package ThinkEat.mvc.controller.Test;

import ThinkEat.mvc.model.dto.PictureDto;
import ThinkEat.mvc.service.PictureService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("Test/")
public class PictureUploadController {
    private final PictureService pictureService;

    @Autowired
    public PictureUploadController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

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
            imagePaths.add(pictureService.getPictureById(picId).getHtmlPath());
        }
        model.addAttribute("imagePaths", imagePaths);
        return "Test/PicUploadTest";
    }

    //使用IMAGEIO的測試
    @GetMapping("/ImageIoTest")
    public String GetImageIoTest() throws IOException {

        return "Test/ImageIoTest";
    }

    //處理IMAGEIO圖片上傳
    @PostMapping("/ImageIoUpload")
    public String imageIoUploadPictures(@RequestPart("pictures") List<MultipartFile> multipartFileList,
                                        RedirectAttributes redirectAttributes) {
        List<String> imageIoPaths = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList) {
            PictureDto pictureDto = new PictureDto();
            Integer picId = pictureService.addPicture(pictureDto, multipartFile);
            imageIoPaths.add(pictureService.getPictureById(picId).getFilePath());
        }
        redirectAttributes.addFlashAttribute("imageIoPaths", imageIoPaths);
        return "Test/ImageIoTest";
    }

    //使用Get圖片
    @GetMapping("/loadImage")
    public void loadImage(@ModelAttribute("imageIoPaths") List<String> imageIoPaths,
                          HttpServletResponse response)
            throws IOException {
        if (imageIoPaths != null && !imageIoPaths.isEmpty()) {
            OutputStream outputStream = response.getOutputStream();
            boolean firstImage = true;

            for (String imagePath : imageIoPaths) {
                File imageFile = new File(imagePath);
                BufferedImage bufferedImage = ImageIO.read(imageFile);

                if (!firstImage) {
                    // If it's not the first image, add a separator (e.g., space)
                    outputStream.write(" ".getBytes());
                } else {
                    firstImage = false;
                }

                // Write the image to the output stream
                ImageIO.write(bufferedImage, "jpg", outputStream);
            }

            // Set the content type of the response
            response.setContentType("image/jpeg");

            // Flush and close the output stream
            outputStream.flush();
            outputStream.close();
        } else {
            // Handle the case when there are no image paths
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }



}

