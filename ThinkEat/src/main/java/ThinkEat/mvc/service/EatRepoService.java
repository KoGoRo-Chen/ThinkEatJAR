package ThinkEat.mvc.service;


import ThinkEat.mvc.model.dto.EatRepoDto;
import ThinkEat.mvc.model.dto.PriceDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.model.dto.TagDto;
import ThinkEat.mvc.model.entity.*;
import ThinkEat.mvc.dao.EatRepoDao;
import ThinkEat.mvc.dao.PriceDao;
import ThinkEat.mvc.dao.TagDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EatRepoService {

    private final EatRepoDao eatRepoDao;
    private final TagDao tagDao;
    private final PriceDao priceDao;
    private final RestaurantService restaurantService;
    private final ModelMapper modelMapper;

    @Autowired
    public EatRepoService(EatRepoDao eatRepoDao,
                          TagDao tagDao,
                          PriceDao priceDao,
                          @Lazy RestaurantService restaurantService,
                          ModelMapper modelMapper) {
        this.eatRepoDao = eatRepoDao;
        this.tagDao = tagDao;
        this.priceDao = priceDao;
        this.restaurantService = restaurantService;
        this.modelMapper = modelMapper;
    }

    //新增文章
    @Transactional
    public Integer addEatRepo(EatRepoDto eatRepoDto) {
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

        //處理價格
        Optional<Price> priceToSet = priceDao.findById(eatRepoDto.getPriceId());
        if (priceToSet.isPresent()) {
            eatRepo.setPrice(priceToSet.get());
        } else {
            eatRepo.setPrice(null);
        }


        //處理標籤
        List<TagDto> tagDtoList = eatRepoDto.getEatRepo_TagList();
        if (tagDtoList != null && !tagDtoList.isEmpty()) {
            List<Tag> tagList = tagDtoList.stream()
                    .map(tagDto -> modelMapper.map(tagDto, Tag.class))
                    .collect(Collectors.toList());
            eatRepo.setEatRepo_TagList(tagList);
        } else {
            eatRepo.setEatRepo_TagList(Collections.emptyList());
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

    //以ID刪除文章
    @Transactional
    public void delete(Integer eatRepoId) {
        Optional<EatRepo> eatRepoOpt = eatRepoDao.findById(eatRepoId);
        if (eatRepoOpt.isPresent()) {
            eatRepoDao.delete(eatRepoOpt.get());
        }
    }

    //以ID尋找單篇食記
    public EatRepoDto getEatRepoByEatRepoId(Integer eatRepoId) {
        Optional<EatRepo> eatRepoOpt = eatRepoDao.findById(eatRepoId);
        if(eatRepoOpt.isPresent()){
            EatRepo eatRepo = eatRepoOpt.get();
            EatRepoDto eatRepoDto = modelMapper.map(eatRepo, EatRepoDto.class);
            return eatRepoDto;
        }
        return null;
    }

    //尋找所有食紀
    public List<EatRepoDto> findAllEatRepo() {
        List<EatRepo> eatRepoList = eatRepoDao.findAll();
        return eatRepoList.stream()
                .map(eatRepo -> modelMapper.map(eatRepo, EatRepoDto.class))
                .toList();
    }


}
