package ThinkEat.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ThinkEat.mvc.model.entity.Restaurant;

public interface RestaurantDao extends JpaRepository<Restaurant, Integer>{

}
