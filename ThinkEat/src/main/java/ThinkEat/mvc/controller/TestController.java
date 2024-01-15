package ThinkEat.mvc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Test/")
public class TestController {

    //顯示上傳圖片頁面
    @GetMapping("/PicUploadTest")
    public String GetRestaurantPage() {
        return "Test/PicUploadTest";
    }
}
