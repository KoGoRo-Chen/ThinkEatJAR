package ThinkEat.mvc.dao;

import ThinkEat.mvc.model.entity.EatRepo;
import ThinkEat.mvc.model.entity.EatRepo_Tag;
import ThinkEat.mvc.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EatRepo_TagDao extends JpaRepository<EatRepo_Tag, Integer> {


}
