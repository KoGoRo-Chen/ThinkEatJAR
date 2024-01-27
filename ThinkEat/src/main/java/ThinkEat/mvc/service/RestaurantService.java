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

    @Autowired
    public RestaurantService(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;

    }

    //新增
    @Transactional
    public Integer addRestaurant(Restaurant restaurant) {
        System.out.println("restaurantService: " + restaurant);
        restaurantDao.save(restaurant);

        // 提取保存後的ID
        Integer restaurantId = restaurant.getId();
        System.out.println("Saved Restaurant ID: " + restaurantId);

        return restaurantId;
    }

    //修改
    @Transactional
    public String updateRestaurant(Integer restaurantId, String name, String address) {
        Optional<Restaurant> resOpt = restaurantDao.findById(restaurantId);
        if (resOpt.isPresent()) {
            Restaurant restaurantToUpdate = resOpt.get();
            restaurantToUpdate.setName(name);
            restaurantToUpdate.setAddress(address);
            restaurantDao.save(restaurantToUpdate);
            return "更改成功";
        }
        return "找不到餐廳";
    }

    //刪除
    @Transactional
    public String deleteRestaurant(Integer restaurantId) {
        Optional<Restaurant> resOpt = restaurantDao.findById(restaurantId);
        if (resOpt.isPresent()) {
            restaurantDao.delete(resOpt.get());
            return "刪除成功";
        }
        return "找不到會員";
    }

    //查詢單間
    public Restaurant getRestaurantById(Integer restaurantId) {
        Optional<Restaurant> resOpt = restaurantDao.findById(restaurantId);
        if (resOpt.isPresent()) {
            Restaurant restaurant = resOpt.get();
            return restaurant;
        }
        return null;
    }

    //查詢所有餐廳
    public List<Restaurant> getAllRestaurant() {
        List<Restaurant> restaurantList = restaurantDao.findAll();
        if (restaurantList != null) {
            return restaurantList;
        }
        return null;
    }

    //查詢所有餐廳(分頁)
    public ShowEatPageDto getAllRestaurant(Pageable pageable) {
        Page<Restaurant> restaurantPage = restaurantDao.findAll(pageable);
        return new ShowEatPageDto(restaurantPage);
    }

    //尋找單間餐廳的所有食記
    public List<EatRepo> getAllEatRepoByRestaurantId(Integer restaurantId) {
        Optional<Restaurant> resOpt = restaurantDao.findById(restaurantId);
        if (resOpt.isPresent()) {
            List<EatRepo> eatRepoList = resOpt.get().getEatRepoList();
            return eatRepoList;
        }
        return Collections.emptyList();
    }

    //尋找單間餐廳的所有食記的所有照片
    public List<Picture> getAllPictureByRestaurantId(Integer restaurantId) {
        Optional<Restaurant> resOpt = restaurantDao.findById(restaurantId);
        if (resOpt.isPresent()) {
            List<Picture> pictureList = resOpt.get().getResPicList();
            return pictureList;
        }
        return null;
    }

    //尋找單間餐廳的所有食記的第一張照片
    public Picture getFirstPictureByRestaurantId(Integer restaurantId) {
        Optional<Restaurant> resOpt = restaurantDao.findById(restaurantId);
        if (resOpt.isPresent()) {
            Restaurant restaurant = resOpt.get();
            if (restaurant.getResPicList() != null) {
                return restaurant.getResPicList().get(0);
            }
        }
        return null;
    }


}
