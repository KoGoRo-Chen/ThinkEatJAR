package ThinkEat.mvc.service;


import ThinkEat.mvc.dao.PictureDao;
import ThinkEat.mvc.model.entity.Picture;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class PictureService {

    private final PictureDao pictureDao;
    private final ModelMapper modelMapper;

    @Autowired
    public PictureService(PictureDao pictureDao, ModelMapper modelMapper) {
        this.pictureDao = pictureDao;
        this.modelMapper = modelMapper;
    }

    // 新增圖片
    @Transactional
    public Integer addPicture(Picture picture,
                              MultipartFile multipartFile) {
        try {

            // 取得圖片名
            String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            picture.setFilename(fileName);

            //取得副檔名
            String originalFilename = multipartFile.getOriginalFilename();
            String formatName = null;
            if (originalFilename != null && originalFilename.contains(".")) {
                formatName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            }
            System.out.println("副檔名為" + formatName);

            // 構建圖片的完整路徑
            String filePath = "C:\\ThinkEat\\images\\";
            /*
            學校電腦圖片資料夾路徑: "C:\\Users\\NTPU\\Desktop\\IdeaProjects\\ThinkEatJAR\\img\\"
            筆電圖片資料夾路徑: "C:\\Users\\marge\\OneDrive\\Desktop\\MyClassDemo\\ThinkEatJAR\\img\\"
            PC圖片資料夾路徑: "C:\\Users\\kogor\\Desktop\\MyJavaProject\\IntelliJ\\ThinkEatJAR\\img\\";
             */
            System.out.println("上傳圖片路徑:" + filePath);

            // 創建 File 對象
            File targetFile = new File(filePath, fileName);

            // 檢查目錄是否存在，不存在則創建
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }

            // 將 MultipartFile 中的檔案讀取為 bytes
            byte[] bytes = multipartFile.getBytes();

            // 將 bytes 轉換為 BufferedImage
            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(bytes));

            // 比較圖片的高度和寬度，並進行相應處理
            if (originalImage.getHeight() > originalImage.getWidth()) {
                // 將高的圖片轉換為768*1024大小
                BufferedImage croppedImage = resizeTallImage(originalImage);
                ImageIO.write(croppedImage, formatName, targetFile);
            } else {
                // 將寬的圖片轉換為1024*768大小
                BufferedImage croppedImage = resizeWideImage(originalImage);
                ImageIO.write(croppedImage, formatName, targetFile);
            }

            // 設定圖片路徑
            picture.setFilePath(filePath + fileName);
            picture.setHtmlPath("http://localhost:9990/ThinkEat/image/" + fileName);
            pictureDao.save(picture);
            return picture.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 將高的圖片縮放成768*1024
    private BufferedImage resizeTallImage(BufferedImage originalImage) {
        // 計算按比例縮放後的寬度
        int desiredWidth = (int) (originalImage.getWidth() * (1024.0 / originalImage.getHeight()));

        // 縮放圖片到指定大小
        Image scaledImage = originalImage.getScaledInstance(desiredWidth, 1024, Image.SCALE_SMOOTH);
        BufferedImage resultImage = new BufferedImage(desiredWidth, 1024, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resultImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        // 計算裁剪區域
        int x = Math.max(0, (desiredWidth - 768) / 2);
        int y = 0;
        int newWidth = Math.min(desiredWidth, 768);

        // 使用 subimage 進行裁剪
        BufferedImage croppedTallImage = resultImage.getSubimage(x, y, newWidth, 1024);
        return croppedTallImage;
    }

    // 將寬的圖片縮放成1024*768
    private BufferedImage resizeWideImage(BufferedImage originalImage) {
        // 計算按比例縮放後的高度
        int desiredHeight = (int) (originalImage.getHeight() * (1024.0 / originalImage.getWidth()));

        // 縮放圖片到指定大小
        Image scaledImage = originalImage.getScaledInstance(1024, desiredHeight, Image.SCALE_SMOOTH);
        BufferedImage resultImage = new BufferedImage(1024, desiredHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resultImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        // 計算裁剪區域
        int x = 0;
        int y = Math.max(0, (desiredHeight - 768) / 2);
        int newHeight = Math.min(desiredHeight, 768);

        // 使用 subimage 進行裁剪
        BufferedImage croppedWideImage = resultImage.getSubimage(x, y, 1024, newHeight);
        return croppedWideImage;
    }


    // 以ID刪除圖片
    @Transactional
    public void deletePicture(Integer pictureId) {
        Optional<Picture> pictureOpt = pictureDao.findById(pictureId);
        if (pictureOpt.isPresent()) {
            pictureDao.delete(pictureOpt.get());
        }
    }

    // 以ID尋找單張圖片
    public Picture getPictureById(Integer pictureId) {
        Optional<Picture> pictureOpt = pictureDao.findById(pictureId);
        if (pictureOpt.isPresent()) {
            Picture picture = pictureOpt.get();
            return picture;
        }
        return null;
    }

    // 尋找所有圖片
    public List<Picture> findAllPicture() {
        List<Picture> pictureList = pictureDao.findAll();
        return pictureList;
    }

}