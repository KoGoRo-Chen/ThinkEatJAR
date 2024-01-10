package ThinkEat.mvc.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import ThinkEat.mvc.entity.jpa.Price;

public interface PriceDao extends JpaRepository<Price, Integer>{

}
