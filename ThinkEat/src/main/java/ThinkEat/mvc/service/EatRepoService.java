package ThinkEat.mvc.service;


import ThinkEat.mvc.model.dto.EatRepoDto;
import ThinkEat.mvc.model.dto.PriceDto;
import ThinkEat.mvc.model.dto.RestaurantDto;
import ThinkEat.mvc.model.entity.*;
import ThinkEat.mvc.dao.EatRepoDao;
import ThinkEat.mvc.dao.PriceDao;
import ThinkEat.mvc.dao.TagDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EatRepoService {

    private final EatRepoDao eatRepoDao;
    private final TagDao tagDataDao;
    private final PriceDao priceDao;
    private final RestaurantService restaurantService;
    private final ModelMapper modelMapper;

    @Autowired
    public EatRepoService(EatRepoDao eatRepoDao, TagDao tagDataDao, PriceDao priceDao, @Lazy RestaurantService restaurantService, ModelMapper modelMapper) {
        this.eatRepoDao = eatRepoDao;
        this.tagDataDao = tagDataDao;
        this.priceDao = priceDao;
        this.restaurantService = restaurantService;
        this.modelMapper = modelMapper;
    }

    //新增文章
    @Transactional
    public EatRepo addEatRepo(EatRepoDto eatRepoDto) {
        EatRepo eatRepo = modelMapper.map(eatRepoDto, EatRepo.class);
        RestaurantDto restaurantDto = eatRepoDto.getRestaurant();
        // 如果 ResDataDto 不為空，則進行相關處理
        if (restaurantDto != null) {
            // 將 ResDataDto 映射為 ResData 對象
            Restaurant restaurant = modelMapper.map(restaurantDto, Restaurant.class);

            // 將 ResData 設置到 EatRepo 中
            eatRepo.setRestaurant(restaurant);
        }

        return eatRepoDao.save(eatRepo);
    }

    //以ID修改文章
    @Transactional
    public void updateEatRepoByEatRepoId(Integer eatRepoId, EatRepoDto eatRepoDto) {
        Optional<EatRepo> eatRepoOpt = eatRepoDao.findById(eatRepoId);
        if (eatRepoOpt.isPresent()) {
            EatRepo eatRepoToUpdate = eatRepoOpt.get();
            modelMapper.map(eatRepoDto, eatRepoToUpdate);
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
