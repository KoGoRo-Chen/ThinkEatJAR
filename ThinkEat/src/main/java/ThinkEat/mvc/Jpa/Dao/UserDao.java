package ThinkEat.mvc.Jpa.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ThinkEat.mvc.Jpa.Entity.User;

public interface UserDao extends JpaRepository<User, Integer>{

}
