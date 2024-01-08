package ThinkEat.mvc.dao;


import ThinkEat.mvc.entity.EatRepo;

import java.util.List;
import java.util.Optional;

public interface EatRepoDao {
	//新增文章
	int addEat(EatRepo eatRepo);

	//以ID修改文章
	int updateEatByEatRepoId(Integer eatRepoId, EatRepo eatRepo);
	
	//以ID刪除文章
	int deleteEatByEatRepoId(Integer eatRepoId);

	//以ID尋找單篇食記
	Optional<EatRepo> getEatByEatRepoId(Integer eatRepoId);

	//尋找所有食紀
	List<EatRepo> findAllEatRepo();
	

	


}
