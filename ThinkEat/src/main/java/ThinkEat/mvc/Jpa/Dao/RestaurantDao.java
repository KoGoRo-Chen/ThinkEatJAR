package ThinkEat.mvc.Jpa.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ThinkEat.mvc.Jpa.Entity.Restaurant;

public interface RestaurantDao extends JpaRepository<Restaurant, Integer>{

}
