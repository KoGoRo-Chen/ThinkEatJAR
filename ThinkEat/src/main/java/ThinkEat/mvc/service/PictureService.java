package ThinkEat.mvc.service;


import ThinkEat.mvc.dao.PictureDao;
import ThinkEat.mvc.dao.PictureDao;
import ThinkEat.mvc.model.dto.PictureDto;
import ThinkEat.mvc.model.dto.PictureDto;
import ThinkEat.mvc.model.entity.Picture;
import ThinkEat.mvc.model.entity.Picture;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Picture addPicture(PictureDto pictureDto) {
        Picture picture = modelMapper.map(pictureDto, Picture.class);
        return pictureDao.save(picture);
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