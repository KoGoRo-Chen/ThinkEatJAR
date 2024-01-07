package ThinkEat.mvc.dao;


import ThinkEat.mvc.bean.PriceData;
import ThinkEat.mvc.bean.TagData;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Repository
public class EatDataDaoImplInMemory implements EatDataDao{

	@Override
	public List<PriceData> findAllPriceDatas() {
		List<PriceData> priceDatas = Arrays.asList(
				new PriceData(1, "低於100"),
				new PriceData(2, "100~200之間"),
				new PriceData(3, "200~300之間"),
				new PriceData(4, "300~400之間"),
				new PriceData(5, "400~500之間"),
				new PriceData(6, "超過500")
				);
		return priceDatas;
	}

	@Override
	public Optional<PriceData> getPriceDataById(Integer id) {
		List<PriceData> priceDatas = findAllPriceDatas();
		return priceDatas.stream().filter(pricedata -> pricedata.getId().equals(id)).findFirst();
	}

	@Override
	public List<TagData> findAllTagDatas() {
		List<TagData> tagDatas = Arrays.asList(
				new TagData(1, "日式"),
				new TagData(2, "中式"),
				new TagData(3, "美式"),
				new TagData(4, "下午茶"),
				new TagData(5, "手搖杯"),
				new TagData(6, "辣")
				);
		return tagDatas;
	}

	@Override
	public Optional<TagData> getTagDataById(Integer id) {
	    if (id == null) {
	        return Optional.empty();
	    }

	    List<TagData> tagDatas = findAllTagDatas();  // 假設 findAllTagDatas 返回的是 TagData[]

	    return tagDatas.stream()
	            .filter(tagData -> tagData.getId().equals(id))
	            .findFirst();
	}

}
