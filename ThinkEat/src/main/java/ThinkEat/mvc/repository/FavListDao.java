package ThinkEat.mvc.repository;

import ThinkEat.mvc.model.entity.FavList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavListDao extends JpaRepository<FavList, Integer> {
}
