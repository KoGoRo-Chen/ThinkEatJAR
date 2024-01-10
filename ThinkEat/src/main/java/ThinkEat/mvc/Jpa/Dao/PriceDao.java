package ThinkEat.mvc.Jpa.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ThinkEat.mvc.Jpa.Entity.Price;

public interface PriceDao extends JpaRepository<Price, Integer>{

}
