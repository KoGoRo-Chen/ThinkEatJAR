package ThinkEat.mvc.dao;

import ThinkEat.mvc.model.entity.Access;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessDao extends JpaRepository<Access, Integer> {
}