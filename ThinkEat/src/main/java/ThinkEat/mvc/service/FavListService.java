package ThinkEat.mvc.service;


import ThinkEat.mvc.dao.FavListDao;
import ThinkEat.mvc.model.dto.FavListDto;
import ThinkEat.mvc.model.entity.FavList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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


    //新增留言
    @Transactional
    public Integer addFavList(FavListDto favListDto) {
        FavList favList = modelMapper.map(favListDto, FavList.class);
        favListDao.save(favList);
        return favList.getId();
    }

    //更新留言
    @Transactional
    public Integer updateCommentById(Integer favListId, FavListDto favListDto) {
        Optional<FavList> favListOpt = favListDao.findById(favListId);
        if (favListOpt.isPresent()) {
            FavList favListToUpdate = favListOpt.get();

            // 更新標題和日期
            favListToUpdate.setFavListName(favListDto.getFavListName());

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

}




