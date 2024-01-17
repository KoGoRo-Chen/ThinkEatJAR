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
import java.util.stream.Collectors;

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

    // 將餐廳從清單中移除
    public List<RestaurantDto> RemoveRestaurantFromFavList(Integer favListId, Integer restaurantId) {
        List<RestaurantDto> restaurantList = new ArrayList<>();

        // 根據 favListId 找出 FavList
        Optional<FavList> favListOpt = favListDao.findById(favListId);

        if (favListOpt.isPresent()) {
            FavList favList = favListOpt.get();

            // 取得 FavList 內的所有食記
            List<EatRepo> eatRepoListInFavList = favList.getFavList_EatRepoList();

            //依餐廳ID尋找所有食記並刪除
            Iterator<EatRepo> iterator = eatRepoListInFavList.iterator();
            while (iterator.hasNext()) {
                EatRepo eatRepo = iterator.next();
                if (eatRepo.getRestaurant().getId().equals(restaurantId)) {
                    iterator.remove();  // 使用 Iterator 刪除元素
                }
            }

            // 更新 FavList 內的食記清單
            favList.setFavList_EatRepoList(eatRepoListInFavList);
            favListDao.save(favList);

            // 重新建立 FavList 內的餐廳列表
            Set<Restaurant> uniqueRestaurants = new HashSet<>();
            for (EatRepo eatRepo : eatRepoListInFavList) {
                Restaurant restaurant = eatRepo.getRestaurant();
                uniqueRestaurants.add(restaurant);
            }

            // 將 uniqueRestaurants 裡的 restaurant 轉換成 Dto
            List<RestaurantDto> restaurantDtoList = uniqueRestaurants.stream()
                    .map(restaurant -> modelMapper.map(restaurant, RestaurantDto.class))
                    .toList();

            return restaurantDtoList;
        }

        return null;
    }


    //隨機抽出指定數量的餐廳
    @Transactional
    public List<RestaurantDto> PickRestaurantDtoByCount(Integer favListId, Integer count) {
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
            // 將 uniqueRestaurants 裡的 restaurant 轉換成 Dto
            List<RestaurantDto> restaurantDtoList = uniqueRestaurants.stream()
                    .map(restaurant -> modelMapper.map(restaurant, RestaurantDto.class))
                    .collect(Collectors.toCollection(ArrayList::new));
            Collections.shuffle(restaurantDtoList);

            List<RestaurantDto> selectedRestaurants = restaurantDtoList.stream()
                    .limit(count)
                    .collect(Collectors.toList());
            return selectedRestaurants;
        }
        return null;
    }






}




