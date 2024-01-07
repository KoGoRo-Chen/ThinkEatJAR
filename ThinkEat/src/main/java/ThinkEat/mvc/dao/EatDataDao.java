package ThinkEat.mvc.dao;



import ThinkEat.mvc.bean.PriceData;
import ThinkEat.mvc.bean.TagData;

import java.util.List;
import java.util.Optional;

public interface EatDataDao {
	
	//收集價位資料
	List<PriceData> findAllPriceDatas();
	Optional<PriceData> getPriceDataById(Integer id);
	
	//收集標籤資料
	List<TagData> findAllTagDatas();
	Optional<TagData> getTagDataById(Integer id);
}
