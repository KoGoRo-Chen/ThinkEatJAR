package ThinkEat.mvc.service;


import ThinkEat.mvc.dao.PictureDao;
import ThinkEat.mvc.model.dto.PictureDto;
import ThinkEat.mvc.model.entity.Picture;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public Integer addPicture(PictureDto pictureDto,
                              MultipartFile multipartFile) {
        try {
            // 取得圖片名
            String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            pictureDto.setFilename(fileName);

            // 構建圖片的完整路徑
            String filePath = "C:\\Users\\kogor\\Desktop\\MyJavaProject\\IntelliJ\\ThinkEatJAR\\img\\";
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

            // 將 MultipartFile 寫入到目標文件前
            // 檢查文件是否已存在
            if (targetFile.exists()) {
                throw new FileAlreadyExistsException("文件已存在");
            } else {
                multipartFile.transferTo(targetFile);
            }

            // 設定圖片路徑
            pictureDto.setFilePath(filePath + fileName);
            pictureDto.setHtmlPath("http://localhost:9990/ThinkEat/image/" + fileName);
            Picture picture = modelMapper.map(pictureDto, Picture.class);
            pictureDao.save(picture);
            return picture.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 以ID修改圖片
    public void updatePictureById(Integer pictureId, PictureDto pictureDto) {
        Optional<Picture> pictureOpt = pictureDao.findById(pictureId);
        if (pictureOpt.isPresent()) {
            Picture updatedPicture = modelMapper.map(pictureDto, Picture.class);
            pictureDao.save(updatedPicture);
        }

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
    public PictureDto getPictureById(Integer pictureId) {
        Optional<Picture> pictureOpt = pictureDao.findById(pictureId);
        if (pictureOpt.isPresent()) {
            Picture picture = pictureOpt.get();
            PictureDto pictureDto = modelMapper.map(picture, PictureDto.class);
            return pictureDto;
        }
        return null;
    }

    // 尋找所有圖片
    public List<PictureDto> findAllPicture() {
        List<Picture> pictureList = pictureDao.findAll();
        return pictureList.stream()
                .map(Picture -> modelMapper.map(Picture, PictureDto.class))
                .toList();
    }

}