package ThinkEat.mvc.dao;

import ThinkEat.mvc.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDao extends JpaRepository<Tag, Integer> {
}
