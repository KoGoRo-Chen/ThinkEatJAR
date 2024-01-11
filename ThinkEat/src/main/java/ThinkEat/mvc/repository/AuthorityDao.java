package ThinkEat.mvc.repository;

import ThinkEat.mvc.model.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityDao extends JpaRepository<Authority, Integer> {
}
