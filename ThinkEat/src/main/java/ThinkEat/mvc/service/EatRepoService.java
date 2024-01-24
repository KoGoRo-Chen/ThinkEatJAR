package ThinkEat.mvc.service;


import ThinkEat.mvc.dao.*;
import ThinkEat.mvc.model.dto.*;
import ThinkEat.mvc.model.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EatRepoService {

    private final EatRepoDao eatRepoDao;
    private final PictureDao pictureDao;
    private final TagDao tagDao;
    private final PriceDao priceDao;
    private final EatRepo_TagDao eatRepo_TagDao;
    private final CommentDao commentDao;
    private final ModelMapper modelMapper;

    @Autowired
    public EatRepoService(EatRepoDao eatRepoDao,
                          PictureDao pictureDao,
                          TagDao tagDao,
                          PriceDao priceDao,
                          EatRepo_TagDao eatRepoTagDao,
                          CommentDao commentDao,
                          ModelMapper modelMapper) {
        this.eatRepoDao = eatRepoDao;
        this.pictureDao = pictureDao;
        this.tagDao = tagDao;
        this.priceDao = priceDao;
        this.eatRepo_TagDao = eatRepoTagDao;
        this.commentDao = commentDao;
        this.modelMapper = modelMapper;
    }

//    //新增只有餐廳資料的空白文章
//    @Transactional
//    public Integer addEatRepoOnlyHaveRestaurant(EatRepoDto eatRepoDto) {
//        System.out.println("eatRepoService: " + eatRepoDto);
//        EatRepo eatRepo = modelMapper.map(eatRepoDto, EatRepo.class);
//
//        //處理餐廳
//        RestaurantDto restaurantDto = eatRepoDto.getRestaurant();
//        if (restaurantDto != null) {
//            Restaurant restaurant = modelMapper.map(restaurantDto, Restaurant.class);
//            eatRepo.setRestaurant(restaurant);
//        } else {
//            eatRepo.setRestaurant(null);
//        }
//
//        //儲存食記並返回ID
//        eatRepoDao.save(eatRepo);
//        return eatRepo.getId();
//    }

    //新增文章
    @Transactional
    public Integer addEatRepo(EatRepo eatRepo) {
        System.out.println("eatRepoService: " + eatRepo);

        //儲存食記並返回ID
        eatRepoDao.save(eatRepo);
        return eatRepo.getId();
    }

    @Transactional
    public void updateEatRepoByEatRepoId(Integer eatRepoId, EatRepo eatRepo) {
        Optional<EatRepo> eatRepoOpt = eatRepoDao.findById(eatRepoId);
        if (eatRepoOpt.isPresent()) {
            EatRepo eatRepoToUpdate = eatRepoOpt.get();

            // 更新標題
            eatRepoToUpdate.setTitle(eatRepo.getTitle());

            // 更新價錢
            Price priceToUpdate = priceDao.findById(eatRepo.getPrice().getId()).orElse(null);
            eatRepoToUpdate.setPrice(priceToUpdate);

            //更新圖片
            List<Picture> pictureList = eatRepo.getPicList().stream()
                                                               .map(pictureDto -> modelMapper.map(pictureDto, Picture.class))
                                                               .collect(Collectors.toList());
            eatRepoToUpdate.setPicList(pictureList);

            // 更新標籤
            List<Tag> tagList = eatRepo.getEatRepo_TagList();
            if (tagList != null && !tagList.isEmpty()) {
                List<Tag> newTagList = tagList.stream()
                        .map(tagDto -> modelMapper.map(tagDto, Tag.class))
                        .collect(Collectors.toList());
                eatRepoToUpdate.setEatRepo_TagList(newTagList);
            } else {
                eatRepoToUpdate.setEatRepo_TagList(Collections.emptyList());
            }

            // 更新文章內容
            eatRepoToUpdate.setArticle(eatRepo.getArticle());

            eatRepoDao.save(eatRepoToUpdate);
        }
    }

    // 以ID刪除單篇食記
    @Transactional
    public void delete(Integer eatRepoId) {
        Optional<EatRepo> eatRepoOpt = eatRepoDao.findById(eatRepoId);
        if (eatRepoOpt.isPresent()) {
            EatRepo eatRepo = eatRepoOpt.get();

            // 手动删除关联实体
            eatRepo.getFavListList().clear();
            eatRepo.getEatRepo_TagList().clear();

            //刪除關聯留言
            List<Comment> commentListToDelete = eatRepo.getCmtList();
            for (Comment commentToDelete : commentListToDelete) {
                commentDao.delete(commentToDelete);
            }

            //刪除關聯圖片
            List<Picture> pictureListToDelete = eatRepo.getPicList();
            for (Picture pictureToDelete : pictureListToDelete) {
                pictureDao.delete(pictureToDelete);
            }

            eatRepoDao.delete(eatRepo);
        }
    }

    //以ID尋找單篇食記
    public EatRepo getEatRepoByEatRepoId(Integer eatRepoId) {
        Optional<EatRepo> eatRepoOpt = eatRepoDao.findById(eatRepoId);
        if (eatRepoOpt.isPresent()) {
            EatRepo eatRepo = eatRepoOpt.get();
            return eatRepo;
        }
        return null;
    }

    //以ID尋找單篇食記中的所有留言
    public List<Comment> findAllCommentByEatRepoId(Integer eatRepoId) {
        EatRepo eatRepo = getEatRepoByEatRepoId(eatRepoId);
        if (eatRepo.getCmtList().isEmpty()) {
            return null;
        } else {
            List<Comment> commentListByEatRepoId = eatRepo.getCmtList();
            return commentListByEatRepoId;
        }
    }

    //尋找所有食紀
    public List<EatRepo> findAllEatRepo() {
        List<EatRepo> eatRepoList = eatRepoDao.findAll();
        return eatRepoList;
    }


}
