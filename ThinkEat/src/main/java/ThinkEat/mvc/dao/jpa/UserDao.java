package ThinkEat.mvc.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ThinkEat.mvc.entity.jpa.User;

public interface UserDao extends JpaRepository<User, Integer>{

}
