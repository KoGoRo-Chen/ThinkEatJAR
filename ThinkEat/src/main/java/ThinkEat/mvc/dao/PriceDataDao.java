package ThinkEat.mvc.dao;

import ThinkEat.mvc.entity.PriceData;


import java.util.List;
import java.util.Optional;

public interface PriceDataDao {
    //新增
    int addPrice(PriceData priceDataData);
    //修改
    int updatePriceByPriceId(Integer tagId, PriceData priceDataData);
    //刪除
    int deletePrice(Integer priceId);
    //以ID尋找單個Tag
    Optional<PriceData> getPriceById(Integer priceId);
    //尋找所有Tag
    List<PriceData> findAllPrice();
}
