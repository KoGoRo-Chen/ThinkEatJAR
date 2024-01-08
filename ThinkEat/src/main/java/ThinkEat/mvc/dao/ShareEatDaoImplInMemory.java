package ThinkEat.mvc.dao;


import ThinkEat.mvc.entity.PriceData;
import ThinkEat.mvc.entity.ResData;
import ThinkEat.mvc.entity.EatRepo;
import ThinkEat.mvc.entity.TagData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	private static final List<ResData> resSum = new CopyOnWriteArrayList<>();
	private static final AtomicInteger atomicShareEatId = new AtomicInteger(0);  //文章ID
	private static final AtomicInteger atomicResId = new AtomicInteger(0);  //餐廳ID

	@Autowired
	@Qualifier("eatDataDaoImplInMemory")
	private EatDataDao eatDataDao;

	//新增文章
	@Override
	public int addEat(EatRepo eatRepo) {
		int eatRepoId = atomicShareEatId.incrementAndGet();
		int resId = atomicResId.incrementAndGet();

		ResData resBean = new ResData();
		resBean.setResId(resId);
		resBean.setResName(eatRepo.getResData().getResName());  // 設置餐廳名稱
		resBean.setResAddress(eatRepo.getResData().getResAddress());  // 設置餐廳地址

		eatRepo.setEatRepoId(eatRepoId);
		eatRepo.setResData(resBean);
		eatsSum.add(eatRepo);
		resSum.add(resBean);

		return 1;
	}

	//以ID尋找文章
	@Override
	public Optional<EatRepo> getEatByShareEatId(Integer shareEatId) {
		Optional<EatRepo> SEBopt = eatsSum.stream().filter(shareEatBean -> shareEatBean.getEatRepoId().equals(shareEatId)).findFirst();
		return SEBopt;
	}

	//以ID修改文章
	@Override
	public int updateEatByShareEatId(Integer shareEatId, EatRepo shareEatBean) {
		Optional<EatRepo> SEBopt = getEatByShareEatId(shareEatId);
		if(SEBopt.isPresent()) {
			EatRepo curEat = SEBopt.get();
			BeanUtils.copyProperties(shareEatBean, curEat);
			return 1;
		}
		return 0;
	}

	//以ID刪除文章
	@Override
	public int deleteEatByShareEatId(Integer shareEatId) {
		Optional<EatRepo> SEBopt = getEatByShareEatId(shareEatId);
		if(SEBopt.isPresent()) {
			eatsSum.remove(SEBopt.get());
			return 1;
		}
		return 0;
	}

	//尋找所有食紀
	@Override
	public List<EatRepo> findAlleatrepo() {
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

	//尋找單間餐廳的所有食紀
	@Override
	public List<EatRepo> findAlleatByresID(Integer resId) {

		//透過stream尋找所有resId相同的shareEatBean
	    List<EatRepo> eatsByResId = eatsSum.stream()
				.filter(EatRepo -> resId.equals(EatRepo.getResData().getResId()))
				.collect(Collectors.toList());

	    //將價格及TAG資料注入shareEatBean
	    eatsByResId.forEach(eatRepo -> {
			//處理價格資料
			Integer priceId = eatRepo.getPriceId();
			Optional<PriceData> priceDataopt = eatDataDao.getPriceDataById(priceId);
			priceDataopt.ifPresent(priceData -> eatRepo.setPrice(priceData));

			//處理TAG資料
			List<TagData> tags = new ArrayList<>();
			for(Integer tagId : eatRepo.getTagIds()) {
				tags.add(eatDataDao.getTagDataById(tagId).get());
			}
			eatRepo.setTags(tags);
		});

		return eatsByResId;
	}

	//尋找所有餐廳
	@Override
	public List<ResData> findAllres() {
		return resSum;
	}

	//尋找單間餐廳
	@Override
	public EatRepo findresByresID(Integer resId) {

		//透過stream尋找所有resId相同的shareEatBean
		EatRepo resByResId = eatsSum.stream()
	            .filter(resBean -> resId.equals(resBean.getResData().getResId())).findFirst().get();

		eatsSum.forEach(resBean -> {
			//將價格資料傳入文章中
			Integer priceId = resBean.getPriceId();
			Optional<PriceData> priceDataopt = eatDataDao.getPriceDataById(priceId);
			priceDataopt.ifPresent(priceData -> resBean.setPrice(priceData));

			//將標籤資料傳入文章中
			List<TagData> tags = new ArrayList<>();
			for(Integer tagId : resBean.getTagIds()) {
				tags.add(eatDataDao.getTagDataById(tagId).get());
			}
			resBean.setTags(tags);
		});

		return resByResId;
	}



}
