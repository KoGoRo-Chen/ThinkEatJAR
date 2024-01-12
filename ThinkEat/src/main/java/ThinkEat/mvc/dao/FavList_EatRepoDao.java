package ThinkEat.mvc.dao;

import ThinkEat.mvc.model.entity.FavList_EatRepo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavList_EatRepoDao extends JpaRepository<FavList_EatRepo, Integer> {
}
