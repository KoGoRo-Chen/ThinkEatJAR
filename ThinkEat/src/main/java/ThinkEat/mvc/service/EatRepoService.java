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
    private final RestaurantService restaurantService;
    private final ModelMapper modelMapper;

    @Autowired
    public EatRepoService(EatRepoDao eatRepoDao,
                          PictureDao pictureDao,
                          TagDao tagDao,
                          PriceDao priceDao, EatRepo_TagDao eatRepoTagDao,
                          @Lazy RestaurantService restaurantService,
                          ModelMapper modelMapper) {
        this.eatRepoDao = eatRepoDao;
        this.pictureDao = pictureDao;
        this.tagDao = tagDao;
        this.priceDao = priceDao;
        eatRepo_TagDao = eatRepoTagDao;
        this.restaurantService = restaurantService;
        this.modelMapper = modelMapper;
    }

    //新增只有餐廳資料的空白文章
    @Transactional
    public Integer addEatRepoOnlyHaveRestaurant(EatRepoDto eatRepoDto) {
        System.out.println("eatRepoService: " + eatRepoDto);
        EatRepo eatRepo = modelMapper.map(eatRepoDto, EatRepo.class);

        //處理餐廳
        RestaurantDto restaurantDto = eatRepoDto.getRestaurant();
        if (restaurantDto != null) {
            Restaurant restaurant = modelMapper.map(restaurantDto, Restaurant.class);
            eatRepo.setRestaurant(restaurant);
        } else {
            eatRepo.setRestaurant(null);
        }

        //儲存食記並返回ID
        eatRepoDao.save(eatRepo);
        return eatRepo.getId();
    }

    @Transactional
    public void updateEatRepoByEatRepoId(Integer eatRepoId, EatRepoDto eatRepoDto) {
        Optional<EatRepo> eatRepoOpt = eatRepoDao.findById(eatRepoId);
        if (eatRepoOpt.isPresent()) {
            EatRepo eatRepoToUpdate = eatRepoOpt.get();

            // 更新標題和日期
            eatRepoToUpdate.setTitle(eatRepoDto.getTitle());
            eatRepoToUpdate.setDate(eatRepoDto.getDate());

            // 更新價錢
            Price priceToUpdate = priceDao.findById(eatRepoDto.getPriceId()).orElse(null);
            eatRepoToUpdate.setPrice(priceToUpdate);

            //更新圖片
            List<Picture> pictureList = eatRepoDto.getPicList().stream()
                                                               .map(pictureDto -> modelMapper.map(pictureDto, Picture.class))
                                                               .collect(Collectors.toList());
            eatRepoToUpdate.setPicList(pictureList);

            // 更新標籤
            List<TagDto> tagDtoList = eatRepoDto.getEatRepo_TagList();
            if (tagDtoList != null && !tagDtoList.isEmpty()) {
                List<Tag> tagList = tagDtoList.stream()
                        .map(tagDto -> modelMapper.map(tagDto, Tag.class))
                        .collect(Collectors.toList());
                eatRepoToUpdate.setEatRepo_TagList(tagList);
            } else {
                eatRepoToUpdate.setEatRepo_TagList(Collections.emptyList());
            }

            // 更新文章內容
            eatRepoToUpdate.setArticle(eatRepoDto.getArticle());

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
            eatRepo.getCmtList().clear();
            eatRepo.getPicList().clear();

            eatRepoDao.delete(eatRepo);
        }
    }

    //以ID尋找單篇食記
    public EatRepoDto getEatRepoByEatRepoId(Integer eatRepoId) {
        Optional<EatRepo> eatRepoOpt = eatRepoDao.findById(eatRepoId);
        if (eatRepoOpt.isPresent()) {
            EatRepo eatRepo = eatRepoOpt.get();
            EatRepoDto eatRepoDto = modelMapper.map(eatRepo, EatRepoDto.class);
            return eatRepoDto;
        }
        return null;
    }

    //以ID尋找單篇食記中的所有留言
    public List<CommentDto> findAllCommentByEatRepoId(Integer eatRepoId) {
        EatRepoDto eatRepoDto = getEatRepoByEatRepoId(eatRepoId);
        if (eatRepoDto.getCmtList().isEmpty()) {
            return null;
        } else {
            List<CommentDto> commentListByRestaurantId = eatRepoDto.getCmtList();
            return commentListByRestaurantId;
        }
    }

    //尋找所有食紀
    public List<EatRepoDto> findAllEatRepo() {
        List<EatRepo> eatRepoList = eatRepoDao.findAll();
        return eatRepoList.stream()
                .map(eatRepo -> modelMapper.map(eatRepo, EatRepoDto.class))
                .toList();
    }


}
