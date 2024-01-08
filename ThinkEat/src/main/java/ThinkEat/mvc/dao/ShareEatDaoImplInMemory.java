package ThinkEat.mvc.dao;


import ThinkEat.mvc.entity.PriceData;
import ThinkEat.mvc.entity.ResData;
import ThinkEat.mvc.entity.EatRepo;
import ThinkEat.mvc.entity.TagData;
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
public class ShareEatDaoImplInMemory implements ShareEatDao{
	//User in Memory資料庫
	private static final List<EatRepo> eatsSum = new CopyOnWriteArrayList<>();
	private static final AtomicInteger atomicShareEatId = new AtomicInteger(0);  //文章ID

	private EatDataDao eatDataDao;
	private ResDataDao resDataDao;

	@Autowired
	public ShareEatDaoImplInMemory(EatDataDao eatDataDao, ResDataDao resDataDao) {
		this.eatDataDao = eatDataDao;
		this.resDataDao = resDataDao;
	}

	//新增文章
	@Override
	public int addEat(EatRepo eatRepo) {
		int eatRepoId = atomicShareEatId.incrementAndGet();
		eatRepo.setEatRepoId(eatRepoId);
		eatsSum.add(eatRepo);
		return 1;
	}

	//以ID修改文章
	@Override
	public int updateEatByEatRepoId(Integer shareEatId, EatRepo shareEatBean) {
		Optional<EatRepo> SEBopt = getEatByEatRepoId(shareEatId);
		if(SEBopt.isPresent()) {
			EatRepo curEat = SEBopt.get();
			BeanUtils.copyProperties(shareEatBean, curEat);
			return 1;
		}
		return 0;
	}

	//以ID刪除文章
	@Override
	public int deleteEatByEatRepoId(Integer shareEatId) {
		Optional<EatRepo> SEBopt = getEatByEatRepoId(shareEatId);
		if(SEBopt.isPresent()) {
			eatsSum.remove(SEBopt.get());
			return 1;
		}
		return 0;
	}

	//以ID尋找單篇食記
	@Override
	public Optional<EatRepo> getEatByEatRepoId(Integer shareEatId) {
		Optional<EatRepo> SEBopt = eatsSum.stream().filter(shareEatBean -> shareEatBean.getEatRepoId().equals(shareEatId)).findFirst();
		return SEBopt;
	}

	//尋找所有食紀
	@Override
	public List<EatRepo> findAllEatRepo() {
		eatsSum.forEach(eatRepo -> {
			//將價格資料傳入文章中
			Integer priceId = eatRepo.getPriceId();
			Optional<PriceData> priceDataopt = eatDataDao.getPriceDataById(priceId);
			priceDataopt.ifPresent(priceData -> eatRepo.setPrice(priceData));

			//將標籤資料傳入文章中
			List<TagData> tags = new ArrayList<>();
			for(Integer tagId : eatRepo.getTagIds()) {
				tags.add(eatDataDao.getTagDataById(tagId).get());
			}
			eatRepo.setTags(tags);
		});

		return eatsSum;
	}


}
