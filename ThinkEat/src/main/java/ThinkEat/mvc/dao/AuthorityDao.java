package ThinkEat.mvc.dao;

import ThinkEat.mvc.model.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityDao extends JpaRepository<Authority, Integer> {
}
