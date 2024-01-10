package ThinkEat.mvc.Service;

import ThinkEat.mvc.Bean.Dao.PriceDataDao;
import ThinkEat.mvc.Bean.Dao.ResDataDao;
import ThinkEat.mvc.Bean.Dao.TagDataDao;
import ThinkEat.mvc.entity.EatRepo;
import ThinkEat.mvc.entity.ResData;
import ThinkEat.mvc.Bean.Dto.EatRepoDto;
import ThinkEat.mvc.Bean.Dto.ResDataDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ResDataService {
    private static final AtomicInteger atomicResId = new AtomicInteger(0);  //餐廳ID
    private static final List<ResDataDto> resDataDtoList = new CopyOnWriteArrayList<>();

    private final EatRepoService eatRepoService;
    private final ResDataDao resDataDao;
    private final TagDataDao tagDataDao;
    private final PriceDataDao priceDataDao;
    private final ModelMapper modelMapper;

    @Autowired
    public ResDataService(@Lazy EatRepoService eatRepoService, ResDataDao resDataDao, TagDataDao tagDataDao, PriceDataDao priceDataDao, ModelMapper modelMapper) {
        this.eatRepoService = eatRepoService;
        this.resDataDao = resDataDao;
        this.tagDataDao = tagDataDao;
        this.priceDataDao = priceDataDao;
        this.modelMapper = modelMapper;
    }

    //新增
    @Transactional
    public int addResData(ResDataDto resDataDto) {
        Integer resDtoId = atomicResId.incrementAndGet();
        resDataDto.setResId(resDtoId);
        resDataDtoList.add(resDataDto);
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
        Optional<ResDataDto> resDataOpt = resDataDtoList.stream()
                .filter(resDataDto -> resDataDto.getResId().equals(resId))
                .findFirst();

        if(resDataOpt.isPresent()){
            ResDataDto resDataDto = resDataOpt.get();
            return resDataDto;
        }
        return null;
    }

    //查詢所有餐廳
    public List<ResDataDto> getAllResData() {
        return resDataDtoList;
    }

    //尋找單間餐廳的所有食記
    public List<EatRepoDto> getAllEatRepoByResId(Integer resId) {
        List<EatRepoDto> eatsDtoByResId = eatRepoService.getAllEatRepoByResId(resId);
        return eatsDtoByResId;
    }


}