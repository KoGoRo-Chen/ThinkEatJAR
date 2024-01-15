package ThinkEat.mvc.dao;

import ThinkEat.mvc.model.entity.EatRepo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EatRepoDao extends JpaRepository<EatRepo, Integer> {

}
