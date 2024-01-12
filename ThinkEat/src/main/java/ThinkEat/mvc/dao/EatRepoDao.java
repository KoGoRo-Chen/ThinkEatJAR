package ThinkEat.mvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ThinkEat.mvc.model.entity.EatRepo;

public interface EatRepoDao extends JpaRepository<EatRepo, Integer>{

}
