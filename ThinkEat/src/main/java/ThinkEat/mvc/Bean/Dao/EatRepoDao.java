package ThinkEat.mvc.Bean.Dao;


import ThinkEat.mvc.entity.EatRepo;

import java.util.List;
import java.util.Optional;

public interface EatRepoDao {
	//新增文章
	int addEatRepo(EatRepo eatRepo);

	//以ID修改文章
	int updateEatRepoByEatRepoId(Integer eatRepoId, EatRepo eatRepo);
	
	//以ID刪除文章
	int deleteEatRepo(Integer eatRepoId);

	//以ID尋找單篇食記
	Optional<EatRepo> getEatRepoByEatRepoId(Integer eatRepoId);

	//尋找所有食紀
	List<EatRepo> findAllEatRepo();
	

	


}
