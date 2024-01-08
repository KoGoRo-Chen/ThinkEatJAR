package ThinkEat.mvc.service;

import ThinkEat.mvc.dao.EatRepoDao;
import ThinkEat.mvc.dao.PriceDataDao;
import ThinkEat.mvc.dao.ResDataDao;
import ThinkEat.mvc.dao.TagDataDao;
import ThinkEat.mvc.entity.EatRepo;
import ThinkEat.mvc.entity.ResData;
import ThinkEat.mvc.entity.dto.EatRepoDto;
import ThinkEat.mvc.entity.dto.ResDataDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResDataService {

    private final ResDataDao resDataDao;
    private final TagDataDao tagDataDao;
    private final PriceDataDao priceDataDao;
    private final ModelMapper modelMapper;

    @Autowired
    public ResDataService(ResDataDao resDataDao, TagDataDao tagDataDao, PriceDataDao priceDataDao, ModelMapper modelMapper) {
        this.resDataDao = resDataDao;
        this.tagDataDao = tagDataDao;
        this.priceDataDao = priceDataDao;
        this.modelMapper = modelMapper;
    }

    //新增
    @Transactional
    public int addResData(ResDataDto resDataDto) {
        ResData resData = modelMapper.map(resDataDto, ResData.class);
        return resDataDao.addResData(resData);
    }

    //修改
    @Transactional
    public ResDataDto updateResData(Integer resId, ResDataDto resDataDto) {
        Optional<ResData> resOpt = resDataDao.getResDataByResID(resId);
        if (resOpt.isPresent()) {
            ResData resDataToUpdate = resOpt.get();
            // 使用 ModelMapper 將更新的資料映射到現有的 ResData 對象
            modelMapper.map(resDataDto, resDataToUpdate);
            resDataDao.updateResDataByResId(resId, resDataToUpdate);
            // 使用 ModelMapper 將更新後的 ResData 對象映射為 ResDataDto
            return modelMapper.map(resDataToUpdate, ResDataDto.class);
        }
        return null; // 返回 null 表示未找到相應的餐廳
    }

    //刪除
    @Transactional
    public int deleteResData(Integer resId) {
        return resDataDao.deleteResDataByResId(resId);
    }

    //查詢單間
    public ResDataDto getResDataById(Integer resId) {
        Optional<ResData> resOpt = resDataDao.getResDataByResID(resId);
        if (resOpt.isPresent()) {
            ResData resDataToShow = resOpt.get();
            ResDataDto resDataDto = modelMapper.map(resDataToShow, ResDataDto.class);
            return resDataDto;
        }
        return null;
    }

    //查詢所有餐廳
    public List<ResDataDto> getAllResData() {
        List<ResData> resDataList = resDataDao.getAllResData();
        return resDataList.stream()
                          .map(resData -> modelMapper.map(resData, ResDataDto.class))
                          .toList();
    }

    //尋找單間餐廳的所有食記
    public List<EatRepoDto> getAllEatRepoByResId(Integer resId) {
        List<EatRepo> eatRepoList = resDataDao.getAllEatRepoByResId(resId);
        return eatRepoList.stream()
                .map(eatRepo -> modelMapper.map(eatRepo, EatRepoDto.class))
                .toList();


    }
}