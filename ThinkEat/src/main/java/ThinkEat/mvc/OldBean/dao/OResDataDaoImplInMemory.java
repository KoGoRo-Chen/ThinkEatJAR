package ThinkEat.mvc.OldBean.dao;

import ThinkEat.mvc.OldBean.EatRepo;
import ThinkEat.mvc.OldBean.ResData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
public class OResDataDaoImplInMemory implements OResDataDao {
    private static final List<ResData> resSum = new CopyOnWriteArrayList<>();

    private OPriceDataDao OPriceDataDao;
    private OTagDataDao OTagDataDao;
    private OEatRepoDao OEatRepoDao;

    @Autowired
    public OResDataDaoImplInMemory(@Lazy OPriceDataDao OPriceDataDao, @Lazy OTagDataDao OTagDataDao, @Lazy OEatRepoDao OEatRepoDao) {
        this.OPriceDataDao = OPriceDataDao;
        this.OTagDataDao = OTagDataDao;
        this.OEatRepoDao = OEatRepoDao;
    }

    @Override
    public int addResData(ResData resData) {
        resSum.add(resData);
        return resData.getResId();
    }

    @Override
    public int updateResDataByResId(Integer resId, ResData resData) {
        Optional<ResData> resOpt = getResDataByResID(resId);
        if(resOpt.isPresent()) {
            ResData curRes = resOpt.get();
            BeanUtils.copyProperties(resData, curRes);
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteResDataByResId(Integer resId) {
        Optional<ResData> resOpt = getResDataByResID(resId);
        if(resOpt.isPresent()) {
            resSum.remove(resOpt.get());
            return 1;
        }
        return 0;
    }

    //尋找單間餐廳
    @Override
    public Optional<ResData> getResDataByResID(Integer resId) {
        Optional<ResData> resOpt = resSum.stream()
                                         .filter(resData -> resData.getResId().equals(resId))
                                         .findFirst();
        return resOpt;
    }

    //尋找所有餐廳
    @Override
    public List<ResData> getAllResData() {
        return resSum;
    }

    //尋找那間餐廳的所有食記
    @Override
    public List<EatRepo> getAllEatRepoByResId(Integer resId) {
        Optional<ResData> resOpt = getResDataByResID(resId);
        List<EatRepo> allEatRepoByResId = new ArrayList<>();

        if (resOpt.isPresent()) {
            // 使用 resId 進行過濾
            allEatRepoByResId = OEatRepoDao.findAllEatRepo().stream()
                    .filter(eatRepo -> resId.equals(eatRepo.getResData().getResId()))
                    .collect(Collectors.toList());
        }

        return allEatRepoByResId;
    }

}
