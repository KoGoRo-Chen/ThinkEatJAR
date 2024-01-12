package ThinkEat.mvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ThinkEat.mvc.model.entity.Price;

public interface PriceDao extends JpaRepository<Price, Integer>{

}
