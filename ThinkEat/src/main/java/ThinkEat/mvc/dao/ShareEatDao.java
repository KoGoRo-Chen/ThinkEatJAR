package ThinkEat.mvc.dao;


import ThinkEat.mvc.bean.EatRepo;

import java.util.List;
import java.util.Optional;

public interface ShareEatDao {
	//新增文章
	int addEat(EatRepo shareEatBean);
	
	//新增餐廳(跟新增文章同步)int addRes(ResBean resBean);
	
	//以ID尋找文章
	Optional<EatRepo> getEatByShareEatId(Integer shareEatId);
	
	//以ID修改文章
	int updateEatByShareEatId(Integer shareEatId, EatRepo shareEatBean);
	
	//以ID刪除文章
	int deleteEatByShareEatId(Integer shareEatId);
	
	//尋找所有食紀
	List<EatRepo> findAlleat();
	
	//尋找單間餐廳的所有食紀
	List<EatRepo> findAlleatByresID(Integer resId);
	
	//尋找所有餐廳
	List<EatRepo> findAllres();
	
	//尋找單間餐廳
	EatRepo findresByresID(Integer resId);

}
