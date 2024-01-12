package ThinkEat.mvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ThinkEat.mvc.model.entity.Restaurant;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantDao extends JpaRepository<Restaurant, Integer>{


}
