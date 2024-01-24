package ThinkEat.mvc.service;


import ThinkEat.mvc.dao.EatRepoDao;
import ThinkEat.mvc.dao.PriceDao;
import ThinkEat.mvc.dao.RestaurantDao;
import ThinkEat.mvc.dao.TagDao;
import ThinkEat.mvc.model.dto.EatRepoDto;
import ThinkEat.mvc.model.dto.PictureDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.model.dto.ShowEatPageDto;
import ThinkEat.mvc.model.entity.EatRepo;
import ThinkEat.mvc.model.entity.Picture;
import ThinkEat.mvc.model.entity.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final RestaurantDao restaurantDao;
    private final ModelMapper modelMapper;

    @Autowired
    public RestaurantService(RestaurantDao restaurantDao,
                             ModelMapper modelMapper) {
        this.restaurantDao = restaurantDao;
        this.modelMapper = modelMapper;
    }

    //新增
    @Transactional
    public Integer addRestaurant(RestaurantDto restaurantDto) {
        System.out.println("restaurantService: " + restaurantDto);

        // 使用save方法保存實體，同時獲取包含ID的實體
        Restaurant newRestaurant = modelMapper.map(restaurantDto, Restaurant.class);
        restaurantDao.save(newRestaurant);

        // 提取保存後的ID
        Integer restaurantId = newRestaurant.getId();
        System.out.println("Saved Restaurant ID: " + restaurantId);

        return restaurantId;
    }

    //修改
    @Transactional
    public void updateRestaurant(Integer restaurantId, RestaurantDto restaurantDto) {
        Optional<Restaurant> resOpt = restaurantDao.findById(restaurantId);
        if (resOpt.isPresent()) {
            Restaurant restaurant = resOpt.get();
            restaurant.setName(restaurantDto.getName());
            restaurantDao.save(restaurant);
        }
        ;
    }

    //刪除
    @Transactional
    public void deleteRestaurant(Integer restaurantId) {
        Optional<Restaurant> resOpt = restaurantDao.findById(restaurantId);
        if (resOpt.isPresent()) {
            restaurantDao.delete(resOpt.get());
        }
    }

    //查詢單間
    public RestaurantDto getRestaurantById(Integer restaurantId) {
        Optional<Restaurant> resOpt = restaurantDao.findById(restaurantId);
        if (resOpt.isPresent()) {
            Restaurant restaurant = resOpt.get();
            RestaurantDto restaurantDto = modelMapper.map(restaurant, RestaurantDto.class);
            return restaurantDto;
        }
        return null;
    }

    //查詢所有餐廳
    public List<RestaurantDto> getAllRestaurant() {
        List<Restaurant> restaurantList = restaurantDao.findAll();
        if (restaurantList != null) {
            return restaurantList.stream()
                    .map(restaurant -> modelMapper.map(restaurant, RestaurantDto.class))
                    .toList();
        }
        return null;
    }

    //查詢所有餐廳(分頁)
    public ShowEatPageDto getAllRestaurant(Pageable pageable) {
        Page<Restaurant> restaurantPage = restaurantDao.findAll(pageable);
        Page<RestaurantDto> restaurantDtoPage = restaurantPage.map(restaurant -> modelMapper.map(restaurant, RestaurantDto.class));
        return new ShowEatPageDto(restaurantDtoPage);
    }

    //尋找單間餐廳的所有食記
    public List<EatRepoDto> getAllEatRepoByRestaurantId(Integer restaurantId) {
        Optional<Restaurant> resOpt = restaurantDao.findById(restaurantId);
        if (resOpt.isPresent()) {
            List<EatRepo> eatRepoList = resOpt.get().getEatRepoList();
            return eatRepoList.stream()
                    .map(eatRepo -> modelMapper.map(eatRepo, EatRepoDto.class))
                    .toList();
        }
        return Collections.emptyList();
    }

    //尋找單間餐廳的所有食記的所有照片
    public List<PictureDto> getAllPictureByRestaurantId(Integer restaurantId) {
        Restaurant restaurant = restaurantDao.findById(restaurantId).get();
        List<PictureDto> pictureDtoList = restaurant.getResPicList().stream()
                .map(picture -> modelMapper.map(picture, PictureDto.class))
                .toList();
        return pictureDtoList;
    }

    //尋找單間餐廳的所有食記的第一張照片
    public PictureDto getFirstPictureByRestaurantId(Integer restaurantId) {
        Restaurant restaurant = restaurantDao.findById(restaurantId).get();
        // 檢查是否有照片
        if (!restaurant.getResPicList().isEmpty()) {
            PictureDto pictureDto = modelMapper.map(restaurant.getResPicList().get(0), PictureDto.class);
            return pictureDto;
        }
        return null;
    }


}
