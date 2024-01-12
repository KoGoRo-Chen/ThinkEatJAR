package ThinkEat.mvc.dao;

import ThinkEat.mvc.model.entity.EatRepo_Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EatRepo_TagDao extends JpaRepository<EatRepo_Tag, Integer> {
}
