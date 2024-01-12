package ThinkEat.mvc.service;


import ThinkEat.mvc.model.dto.EatRepoDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.model.entity.EatRepo;
import ThinkEat.mvc.model.entity.Restaurant;
import ThinkEat.mvc.dao.EatRepoDao;
import ThinkEat.mvc.dao.PriceDao;
import ThinkEat.mvc.dao.RestaurantDao;
import ThinkEat.mvc.dao.TagDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final EatRepoService eatRepoService;
    private final EatRepoDao eatRepoDao;
    private final TagDao tagDataDao;
    private final PriceDao priceDao;
    private final RestaurantDao restaurantDao;
    private final ModelMapper modelMapper;

    @Autowired
    public RestaurantService(EatRepoService eatRepoService, EatRepoDao eatRepoDao, TagDao tagDataDao, PriceDao priceDao, RestaurantDao restaurantDao, ModelMapper modelMapper) {
        this.eatRepoService = eatRepoService;
        this.eatRepoDao = eatRepoDao;
        this.tagDataDao = tagDataDao;
        this.priceDao = priceDao;
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
        resOpt.ifPresent(restaurantToUpdate -> {
            modelMapper.map(restaurantDto, restaurantToUpdate);
            restaurantDao.save(restaurantToUpdate);
        });
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
        return restaurantList.stream()
                .map(restaurant -> modelMapper.map(restaurant, RestaurantDto.class))
                .toList();
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


}
/*
//新增
@Transactional
public Restaurant addRestaurant(RestaurantDto newRestaurantDto) {
    RestaurantDto newRestaurantDto = new RestaurantDto();
    newRestaurantDto.setName(name);
    newRestaurantDto.setAddress(address);
    Restaurant newrestaurant = modelMapper.map(newRestaurantDto, Restaurant.class);
    System.out.println("restaurantService: " + newRestaurantDto);
    return restaurantDao.save(newrestaurant);
}
 */