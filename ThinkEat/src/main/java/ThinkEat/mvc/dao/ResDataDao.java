package ThinkEat.mvc.dao;

import ThinkEat.mvc.entity.EatRepo;
import ThinkEat.mvc.entity.ResData;

import java.util.List;
import java.util.Optional;

public interface ResDataDao {

    //新增
    int addResData(ResData resData);

    //修改
    int updateResDataByResId(Integer resId, ResData resData);

    //刪除
    int deleteResDataByResId(Integer resId);

    //以ID尋找單間餐廳
    Optional<ResData> getResByResID(Integer resId);

    //尋找所有餐廳
    List<ResData> getAllResData();

}
