package ThinkEat.mvc.Jpa.Dao;

import ThinkEat.mvc.Jpa.Entity.FavList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavListDao extends JpaRepository<FavList, Integer> {
}
