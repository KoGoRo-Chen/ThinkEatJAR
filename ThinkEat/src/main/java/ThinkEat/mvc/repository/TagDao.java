package ThinkEat.mvc.repository;

import ThinkEat.mvc.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDao extends JpaRepository<Tag, Integer> {
}
