package ThinkEat.mvc.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ThinkEat.mvc.entity.jpa.Restaurant;

public interface RestaurantDao extends JpaRepository<Restaurant, Integer>{

}
