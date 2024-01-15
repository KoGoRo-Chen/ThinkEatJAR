package ThinkEat.mvc.dao;

import ThinkEat.mvc.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {

}
