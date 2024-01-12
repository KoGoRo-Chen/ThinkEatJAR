package ThinkEat.mvc.dao;

import ThinkEat.mvc.model.entity.FavList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavListDao extends JpaRepository<FavList, Integer> {
}
