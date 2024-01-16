package ThinkEat.mvc.service;


import ThinkEat.mvc.dao.FavListDao;
import ThinkEat.mvc.model.dto.EatRepoDto;
import ThinkEat.mvc.model.dto.FavListDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.model.entity.EatRepo;
import ThinkEat.mvc.model.entity.FavList;
import ThinkEat.mvc.model.entity.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class FavListService {

    private final FavListDao favListDao;
    private final RestaurantService restaurantService;
    private final EatRepoService eatRepoService;
    private final ModelMapper modelMapper;

    @Autowired
    public FavListService(FavListDao favListDao, RestaurantService restaurantService, EatRepoService eatRepoService, ModelMapper modelMapper) {
        this.favListDao = favListDao;
        this.restaurantService = restaurantService;
        this.eatRepoService = eatRepoService;
        this.modelMapper = modelMapper;
    }


    //新增清單
    @Transactional
    public Integer addFavList(FavListDto favListDto) {
        FavList favList = modelMapper.map(favListDto, FavList.class);
        favListDao.save(favList);
        return favList.getId();
    }

    //更新清單
    @Transactional
    public Integer updateFavListById(Integer favListId, FavListDto favListDto) {
        Optional<FavList> favListOpt = favListDao.findById(favListId);
        if (favListOpt.isPresent()) {
            FavList favListToUpdate = favListOpt.get();

            // 更新標題
            favListToUpdate.setFavListName(favListDto.getFavListName());

            // 更新文章
            List<EatRepoDto> eatRepoDtoList = favListDto.getFavList_EatRepoList();
            List<EatRepo> eatRepoList = eatRepoDtoList.stream()
                    .map(eatRepoDto -> modelMapper.map(eatRepoDto, EatRepo.class))
                    .toList();
            favListToUpdate.setFavList_EatRepoList(eatRepoList);

            //儲存更新
            favListDao.save(favListToUpdate);
            return favListToUpdate.getId();
        }
        return null;
    }

    //將食記加入至清單中
    @Transactional
    public Integer addEatRepoToFavListById(Integer favListId, Integer eatRepoId) {
        Optional<FavList> favListOpt = favListDao.findById(favListId);
        if (favListOpt.isPresent()) {
            FavList favListToUpdate = favListOpt.get();

            //將EatRepoDto轉換成EatRepo
            EatRepoDto eatRepoDto = eatRepoService.getEatRepoByEatRepoId(eatRepoId);
            EatRepo eatRepo = modelMapper.map(eatRepoDto, EatRepo.class);

            //將EatRepo存進清單
            favListToUpdate.getFavList_EatRepoList().add(eatRepo);

            //儲存更新
            favListDao.save(favListToUpdate);
            return favListToUpdate.getId();
        }
        return null;
    }


    //以ID刪除清單
    @Transactional
    public void deleteFavListById(Integer favListId) {
        Optional<FavList> favListOpt = favListDao.findById(favListId);
        if (favListOpt.isPresent()) {
            FavList favListToDelete = favListOpt.get();
            favListDao.delete(favListToDelete);
        }
    }

    //以ID尋找清單
    public FavListDto getFavListById(Integer favListId) {
        Optional<FavList> favListOpt = favListDao.findById(favListId);
        if (favListOpt.isPresent()) {
            FavList favList = favListOpt.get();
            FavListDto favListDto = modelMapper.map(favList, FavListDto.class);
            return favListDto;
        }
        return null;
    }

    //尋找所有清單
    public List<FavListDto> findAllFavList() {
        List<FavList> favListList = favListDao.findAll();
        return favListList.stream()
                .map(favList -> modelMapper.map(favList, FavListDto.class))
                .toList();
    }

    //尋找清單裡的所有餐廳
    public List<RestaurantDto> findAllRestaurantsInFavList(Integer favListId) {
        List<RestaurantDto> restaurantList = new ArrayList<>();

        // 根據 favListId 找出 FavList
        Optional<FavList> favListOpt = favListDao.findById(favListId);

        if (favListOpt.isPresent()) {
            FavList favList = favListOpt.get();

            // 取得 FavList 內的所有食記
            List<EatRepo> eatRepoListInFavList = favList.getFavList_EatRepoList();

            // 用 Set 來確保餐廳不會重複
            Set<Restaurant> uniqueRestaurants = new HashSet<>();

            // 遍歷每一個食記，取得對應的餐廳
            for (EatRepo eatRepo : eatRepoListInFavList) {
                Restaurant restaurant = eatRepo.getRestaurant();

                // 將餐廳加入 Set
                uniqueRestaurants.add(restaurant);
            }
            // 將uniqueRestaurants裡的restaurant轉換成Dto
            List<RestaurantDto> restaurantDtoList = uniqueRestaurants.stream()
                    .map(restaurant -> modelMapper.map(restaurant, RestaurantDto.class))
                    .toList();
            //儲存restaurantList
            restaurantList.addAll(restaurantDtoList);
        }

        return restaurantList;
    }


}




