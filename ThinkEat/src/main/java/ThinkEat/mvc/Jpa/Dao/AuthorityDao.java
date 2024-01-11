package ThinkEat.mvc.Jpa.Dao;

import ThinkEat.mvc.Jpa.Entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityDao extends JpaRepository<Authority, Integer> {
}
