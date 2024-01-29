package ThinkEat.mvc.service;


import ThinkEat.mvc.dao.FavListDao;
import ThinkEat.mvc.model.dto.FavListPageDto;
import ThinkEat.mvc.model.dto.PricePageDto;
import ThinkEat.mvc.model.dto.RestaurantPageDto;
import ThinkEat.mvc.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FavListService {

    private final FavListDao favListDao;
    private final RestaurantService restaurantService;
    private final EatRepoService eatRepoService;
    private final UserService userService;

    @Autowired
    public FavListService(FavListDao favListDao,
                          RestaurantService restaurantService,
                          EatRepoService eatRepoService,
                          @Lazy UserService userService) {
        this.favListDao = favListDao;
        this.restaurantService = restaurantService;
        this.eatRepoService = eatRepoService;
        this.userService = userService;
    }

    //新增訪客清單
    @Transactional
    public Integer addGuestList(FavList favList) {
        favListDao.save(favList);
        return favList.getId();
    }

    //新增清單
    @Transactional
    public Integer addFavList(FavList favList) {
        Integer max = favList.getFavList_User().getFavLists().size();
        favList.setListCount(max + 1);
        favListDao.save(favList);
        return favList.getId();
    }

    //更新清單
    @Transactional
    public Integer updateFavListById(Integer favListId, String name) {
        Optional<FavList> favListOpt = favListDao.findById(favListId);
        if (favListOpt.isPresent()) {
            FavList favListToUpdate = favListOpt.get();

            // 更新標題
            favListToUpdate.setName(name);

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

            EatRepo eatRepo = eatRepoService.getEatRepoByEatRepoId(eatRepoId);

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
    public FavList getFavListById(Integer favListId) {
        Optional<FavList> favListOpt = favListDao.findById(favListId);
        if (favListOpt.isPresent()) {
            FavList favList = favListOpt.get();
            return favList;
        }
        return null;
    }

    //尋找所有清單
    public List<FavList> findAllFavList() {
        List<FavList> favListList = favListDao.findAll();
        return favListList;

    }

    //尋找清單裡的所有餐廳
    public List<Restaurant> findAllRestaurantsInFavList(Integer favListId) {
        // 用 Set 來確保餐廳不會重複
        Set<Restaurant> uniqueRestaurants = new HashSet<>();

        // 根據 favListId 找出 FavList
        Optional<FavList> favListOpt = favListDao.findById(favListId);

        if (favListOpt.isPresent()) {
            FavList favList = favListOpt.get();

            // 取得 FavList 內的所有食記
            List<EatRepo> eatRepoListInFavList = favList.getFavList_EatRepoList();

            // 遍歷每一個食記，取得對應的餐廳
            for (EatRepo eatRepo : eatRepoListInFavList) {
                Restaurant restaurant = eatRepo.getRestaurant();

                // 將餐廳加入 Set
                uniqueRestaurants.add(restaurant);
            }

        }
        //儲存restaurantList
        List<Restaurant> restaurantList = uniqueRestaurants.stream().toList();

        return restaurantList;
    }

    //查詢清單裡的所有餐廳(分頁)
    public RestaurantPageDto getAllRestaurantInFavList(Pageable pageable,
                                                       Integer favListId) {

        Set<Restaurant> uniqueRestaurants = new HashSet<>();

        // 根據 favListId 找出 FavList
        Optional<FavList> favListOpt = favListDao.findById(favListId);

        if (favListOpt.isPresent()) {
            FavList favList = favListOpt.get();

            // 取得 FavList 內的所有食記
            List<EatRepo> eatRepoListInFavList = favList.getFavList_EatRepoList();

            // 遍歷每一個食記，取得對應的餐廳
            for (EatRepo eatRepo : eatRepoListInFavList) {
                Restaurant restaurant = eatRepo.getRestaurant();
                // 將餐廳加入 Set
                uniqueRestaurants.add(restaurant);
            }
        }

        // 轉換成分頁對象
        List<Restaurant> restaurantList = new ArrayList<>(uniqueRestaurants);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), restaurantList.size());

        List<Restaurant> paginatedList = restaurantList.subList(start, end);

        Page<Restaurant> restaurantPage = new PageImpl<>(paginatedList, pageable, restaurantList.size());

        return new RestaurantPageDto(restaurantPage);
    }

    //尋找所有清單(分頁)
    public FavListPageDto getAllFavListInPage(Pageable pageable) {
        Page<FavList> favListPage = favListDao.findAll(pageable);
        return new FavListPageDto(favListPage);
    }

    // 將餐廳從清單中移除
    public List<Restaurant> removeRestaurantFromFavList(Integer favListId, Integer restaurantId) {
        // 根據 favListId 找出 FavList
        Optional<FavList> favListOpt = favListDao.findById(favListId);

        if (favListOpt.isPresent()) {
            FavList favList = favListOpt.get();

            // 取得 FavList 內的所有食記
            List<EatRepo> eatRepoListInFavList = favList.getFavList_EatRepoList();

            // 依餐廳ID尋找所有食記並刪除
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

            return new ArrayList<>(uniqueRestaurants);
        }

        return null;
    }


    // 隨機抽出指定數量的餐廳
    @Transactional
    public List<Restaurant> pickRestaurantsByCount(Integer favListId, Integer count) {
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

            List<Restaurant> restaurantList = new ArrayList<>(uniqueRestaurants);
            Collections.shuffle(restaurantList);

            return restaurantList.stream()
                    .limit(count)
                    .collect(Collectors.toList());
        }

        return null;
    }

    // 將抽選結果進行分頁
    public RestaurantPageDto getGachaResult(List<Integer> gachaResult,
                                            Pageable pageable) {
        List<Restaurant> restaurantList = new ArrayList<>();
        for (Integer restaurantId : gachaResult) {
            Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
            restaurantList.add(restaurant);
        }

        // 將餐廳列表轉換成分頁對象
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Restaurant> pageRestaurantList;

        if (restaurantList.size() < startItem) {
            pageRestaurantList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, restaurantList.size());
            pageRestaurantList = restaurantList.subList(startItem, toIndex);
        }

        // 創建分頁對象並返回
        Page<Restaurant> gachaPage = new PageImpl<>(pageRestaurantList, pageable, restaurantList.size());
        return new RestaurantPageDto(gachaPage);
    }


    //從ListCount及會員找到對應的清單
    public FavList findFavListByUserAndListCount(Integer userId, Integer listcount) {
        User user = userService.getUserById(userId);
        List<FavList> favLists = user.getFavLists();
        Optional<FavList> matchingList = favLists.stream()
                .filter(favList -> favList.getListCount().equals(listcount))
                .findFirst();
        return matchingList.orElse(null);
    }

}




