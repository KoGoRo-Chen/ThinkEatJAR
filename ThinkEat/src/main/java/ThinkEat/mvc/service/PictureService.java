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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class PictureService {

    private final PictureDao pictureDao;
    private final ModelMapper modelMapper;

    @Value("${upload.dir}")
    private String uploadDir;

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

            // 構建文件的完整路徑
            String relativePath = "static/img/" + fileName;
            String filePath = ResourceUtils.getURL("classpath:").getPath() + relativePath;
            String realPath = filePath.replace('/', '\\').substring(1);
            System.out.println(realPath);
            // 創建 File 對象
            File targetFile = new File(realPath);

            // 將 MultipartFile 寫入到目標文件
            multipartFile.transferTo(targetFile);

            // 設定圖片路徑
            pictureDto.setPath(realPath);
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