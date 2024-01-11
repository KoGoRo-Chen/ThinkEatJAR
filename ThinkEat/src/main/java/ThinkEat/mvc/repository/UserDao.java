package ThinkEat.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ThinkEat.mvc.model.entity.User;

public interface UserDao extends JpaRepository<User, Integer>{

}
