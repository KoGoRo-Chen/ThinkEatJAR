package ThinkEat.mvc;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ThinkEat.mvc.dao.jpa.EatRepoDao;
import ThinkEat.mvc.dao.jpa.RestaurantDao;
import ThinkEat.mvc.dao.jpa.UserDao;
import ThinkEat.mvc.entity.jpa.EatRepo;
import ThinkEat.mvc.entity.jpa.Restaurant;
import ThinkEat.mvc.entity.jpa.User;
import jakarta.transaction.Transactional;

@SpringBootTest
public class SpringDaoTest {

	@Autowired
	UserDao userDao;
	
	@Autowired
	EatRepoDao eatRepoDao;
	
	@Autowired
	RestaurantDao restaurantDao;
	
	//@Test
	void createUser() {
		
		User user = new User();
		user.setLevelId(2);
		user.setNickName("nickname");
		user.setPassword("123");
		user.setUseraccount("account");
		userDao.save(user);
		
		Restaurant restaurant = new Restaurant();
		restaurant.setName("麥當勞");
		restaurant.setAddress("台北市建國路一段456號");
		restaurantDao.save(restaurant);
		
		EatRepo eatRepo = new EatRepo();
		eatRepo.setUser(user);
		eatRepo.setEatTitle("title");
		eatRepo.setEatrepo("context");
		eatRepo.setPriceId(1);
		eatRepo.setRestaurant(restaurant);
		eatRepoDao.save(eatRepo);
		
	}

	@Test
	@Transactional
	void queryUser() {
		User user = userDao.findById(1).get();
		List<EatRepo> eatRepos = user.getEatRepos();
		for(EatRepo eatRepo:eatRepos) {
			Restaurant restaurant = eatRepo.getRestaurant();
			System.out.printf(
					"user=%s,epoId=%d,eatTitle=%s,restaurant=%s%n",
					user.getUseraccount(),
					eatRepo.getId(),
					eatRepo.getEatTitle(),
					restaurant.getName());
		}
	}
	
	//@Test
	//@Transactional
	void queryRestaurant() {
		Restaurant restaurant = restaurantDao.findById(1).get();
		List<EatRepo> eatRepos = restaurant.getEatRepos();
		for(EatRepo eatRepo:eatRepos) {
			System.out.println(eatRepo.getRestaurant());
		}
	}
	
}
