package ThinkEat.mvc.dao;

import ThinkEat.mvc.entity.EatRepo;
import ThinkEat.mvc.entity.ResData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class ResDataDaoImplInMemory implements ResDataDao{
    private static final List<ResData> resSum = new CopyOnWriteArrayList<>();
    private static final AtomicInteger atomicResId = new AtomicInteger(0);  //餐廳ID


    private EatDataDao eatDataDao;
    private EatRepoDao eatRepoDao;

    @Autowired
    public ResDataDaoImplInMemory(EatDataDao eatDataDao, EatRepoDao eatRepoDao) {
        this.eatDataDao = eatDataDao;
        this.eatRepoDao = eatRepoDao;
    }

    @Override
    public int addResData(ResData resData) {
        int resId = atomicResId.incrementAndGet();
        resData.setResId(resId);
        resSum.add(resData);
        return 0;
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
            allEatRepoByResId = eatRepoDao.findAllEatRepo().stream()
                    .filter(eatRepo -> resId.equals(eatRepo.getResData().getResId()))
                    .collect(Collectors.toList());
        }

        return allEatRepoByResId;
    }

}
