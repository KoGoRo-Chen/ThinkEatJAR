package ThinkEat.mvc.OldBean.dao;


import ThinkEat.mvc.OldBean.PriceData;
import ThinkEat.mvc.OldBean.EatRepo;
import ThinkEat.mvc.OldBean.TagData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class EatRepoDaoImplInMemory implements EatRepoDao {
	//User in Memory資料庫
	private static final List<EatRepo> eatsSum = new CopyOnWriteArrayList<>();


	private PriceDataDao priceDataDao;
	private TagDataDao tagDataDao;

	@Autowired
	public EatRepoDaoImplInMemory(PriceDataDao priceDataDao, TagDataDao tagDataDao) {
		this.priceDataDao = priceDataDao;
		this.tagDataDao = tagDataDao;
	}

	//新增文章
	@Override
	public int addEatRepo(EatRepo eatRepo) {
		eatsSum.add(eatRepo);
		return eatRepo.getEatRepoId();
	}

	//以ID修改文章
	@Override
	public int updateEatRepoByEatRepoId(Integer shareEatId, EatRepo shareEatBean) {
		Optional<EatRepo> SEBopt = getEatRepoByEatRepoId(shareEatId);
		if(SEBopt.isPresent()) {
			EatRepo curEat = SEBopt.get();
			BeanUtils.copyProperties(shareEatBean, curEat);
			return 1;
		}
		return 0;
	}

	//以ID刪除文章
	@Override
	public int deleteEatRepo(Integer shareEatId) {
		Optional<EatRepo> SEBopt = getEatRepoByEatRepoId(shareEatId);
		if(SEBopt.isPresent()) {
			eatsSum.remove(SEBopt.get());
			return 1;
		}
		return 0;
	}

	//以ID尋找單篇食記
	@Override
	public Optional<EatRepo> getEatRepoByEatRepoId(Integer shareEatId) {
		Optional<EatRepo> SEBopt = eatsSum.stream().filter(shareEatBean -> shareEatBean.getEatRepoId().equals(shareEatId)).findFirst();
		return SEBopt;
	}

	//尋找所有食紀
	@Override
	public List<EatRepo> findAllEatRepo() {
		eatsSum.forEach(eatRepo -> {
			//將價格資料傳入文章中
			Integer priceId = eatRepo.getPriceId();
			Optional<PriceData> priceDataopt = priceDataDao.getPriceById(priceId);
			priceDataopt.ifPresent(priceData -> eatRepo.setPrice(priceData));

			//將標籤資料傳入文章中
			List<TagData> tags = new ArrayList<>();
			for(Integer tagId : eatRepo.getTagIds()) {
				tags.add(tagDataDao.getTagByTagId(tagId).get());
			}
			eatRepo.setTags(tags);
		});

		return eatsSum;
	}


}
