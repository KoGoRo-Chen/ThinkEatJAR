package ThinkEat.mvc.Jpa.Dao;

import ThinkEat.mvc.Jpa.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDao extends JpaRepository<Tag, Integer> {
}
