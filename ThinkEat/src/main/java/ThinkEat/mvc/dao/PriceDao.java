package ThinkEat.mvc.dao;

import ThinkEat.mvc.model.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceDao extends JpaRepository<Price, Integer> {

}
