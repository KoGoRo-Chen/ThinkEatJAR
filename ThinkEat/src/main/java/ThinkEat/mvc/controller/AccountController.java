package ThinkEat.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

@Controller
@RequestMapping("/Account")
//網頁路徑：http://localhost:8080/ThinkEat/mvc/Account
public class AccountController {

    @Autowired
    @Qualifier("shareEatDaoImplInMemory")
    private ShareEatDao shareEatDao;

    @Autowired
    @Qualifier("eatDataDaoImplInMemory")
    private EatDataDao eatDataDao;

    @Autowired
    @Qualifier("userBeanDaoImplInMemory")
    private UserBeanDao userBeanDao;

    // 顯示登入頁面
    @GetMapping(value = { "/login"})
    public String GetLoginPage(HttpServletRequest request, HttpSession session) {
        String currentPath = request.getRequestURI();
        session.setAttribute("requestedUrl", currentPath);
        return "login";
    }

    @GetMapping("/getcode")
    private void getCodeImage(HttpSession session, HttpServletResponse response) throws IOException {
        // 產生一個驗證碼 code
        Random random = new Random();
        String code1 = String.format("%c", (char)(random.nextInt(122-65+1) + 65));
        String code2 = String.format("%c", (char)(random.nextInt(122-65+1) + 65));
        String code3 = String.format("%c", (char)(random.nextInt(122-65+1) + 65));
        String code4 = String.format("%c", (char)(random.nextInt(122-65+1) + 65));

        String code  = code1+code2+code3+code4;
        session.setAttribute("code", code);

        // Java 2D 產生圖檔
        // 1. 建立圖像暫存區
        BufferedImage img = new BufferedImage(80, 30, BufferedImage.TYPE_INT_BGR);
        // 2. 建立畫布
        Graphics g = img.getGraphics();
        // 3. 設定顏色
        g.setColor(Color.YELLOW);
        // 4. 塗滿背景
        g.fillRect(0, 0, 80, 30);
        // 5. 設定顏色
        g.setColor(Color.BLACK);
        // 6. 設定自型
        g.setFont(new Font("新細明體", Font.PLAIN, 30));
        // 7. 繪字串(code = String code  = code1+code2+code3+code4;)
        g.drawString(code, 10, 23); // code, x, y
        // 8. 干擾線
        g.setColor(Color.RED);
        for(int i=0;i<10;i++) {
            int x1 = random.nextInt(80);
            int y1 = random.nextInt(30);
            int x2 = random.nextInt(80);
            int y2 = random.nextInt(30);
            g.drawLine(x1, y1, x2, y2);
        }

        // 設定回應類型
        response.setContentType("image/png");

        // 將影像串流回寫給 client
        ImageIO.write(img, "PNG", response.getOutputStream());
    }

    // 前台登入處理
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("code") String code, HttpSession session, Model model) throws Exception {
        /*
         * 比對驗證碼 if(!code.equals(session.getAttribute("code")+"")) {
         * session.invalidate(); // session 過期失效 model.addAttribute("loginMessage",
         * "驗證碼錯誤");return "group_buy/login"; }
         */

        // 根據 username 查找 user 物件
        Optional<UserBean> userOpt = userBeanDao.findUserByUsername(username);
        if (userOpt.isPresent()) {
            UserBean user = userOpt.get();
            // 將 password 進行 AES 加密
            // -------------------------------------------------------------------
            final String KEY = KeyUtil.generateSecret(256);
            SecretKeySpec aesKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            byte[] encryptedPasswordECB = KeyUtil.encryptWithAESKey(aesKeySpec, password);
            String encryptedPasswordECBBase64 = Base64.getEncoder().encodeToString(encryptedPasswordECB);
            // -------------------------------------------------------------------------------------------
            if (user.getUserPW().equals(encryptedPasswordECBBase64)) { // 比對加密過後的 password 是否相同
                session.setAttribute("user", user); // 將 user 物件放入到 session 變數中
                String requestedUrl = (String) session.getAttribute("requestedUrl");
                if (requestedUrl != null) {
                    // 清除 Session 中的路徑
                    session.removeAttribute("requestedUrl");
                    // 重定向到原始路徑
                    return "redirect:" + requestedUrl;
                } else {
                    // 登入成功後的默認頁面
                    return "redirect:/mvc/ThinkEatIndex";
                }
            } else {
                session.invalidate(); // session 過期失效
                model.addAttribute("loginMessage", "密碼錯誤");
                return "login";
            }
        } else {
            session.invalidate(); // session 過期失效
            model.addAttribute("loginMessage", "無此使用者");
            return "login";
        }
    }

    // 顯示會員中心頁面
    @GetMapping(value = { "/AccountCenter/{userId}"})
    public String GetAccountCenterPage(@PathVariable("userId") Integer userId, HttpSession session, Model model) {
        // 1. 根據 resId 從數據庫中檢索相應的 res
        UserBean userByUserId = userBeanDao.findUserById(userId).get();

        // 2. 將檢索到的 res 及ID添加到模型中
        model.addAttribute("userId", userByUserId);

        // 返回 ViewEat 頁面
        return "AccountCenter";
    }

    // 顯示修改密碼頁面
    @GetMapping(value = { "/UpdatePW/{userId}"})
    public String GetUpdatePWPage(@PathVariable("userId") Integer userId, HttpSession session, Model model) {
        UserBean userToUpdatePW = userBeanDao.findUserById(userId).get();
        String userPWToUpdate = userToUpdatePW.getUserPW();

        model.addAttribute("userPW", userPWToUpdate);

        // 返回 ViewEat 頁面
        return "UpdatePW";
    }

    // 顯示修改密碼頁面
    @PostMapping(value = { "/UpdatePW/{userId}"})
    public String UpdatePWbyUserId(@PathVariable("userId") Integer userId, HttpSession session, Model model) {


        // 返回 ViewEat 頁面
        return "UpdatePW";
    }

}
