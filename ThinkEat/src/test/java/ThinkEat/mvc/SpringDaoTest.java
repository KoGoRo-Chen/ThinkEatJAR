package ThinkEat.mvc;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ThinkEat.mvc.Jpa.Dao.EatRepoDao;
import ThinkEat.mvc.Jpa.Dao.PriceDao;
import ThinkEat.mvc.Jpa.Dao.RestaurantDao;
import ThinkEat.mvc.Jpa.Dao.UserDao;
import ThinkEat.mvc.Jpa.Entity.EatRepo;
import ThinkEat.mvc.Jpa.Entity.Price;
import ThinkEat.mvc.Jpa.Entity.Restaurant;
import ThinkEat.mvc.Jpa.Entity.User;
import jakarta.transaction.Transactional;

@SpringBootTest
public class SpringDaoTest {

	@Autowired
	UserDao userDao;
	
	@Autowired
	EatRepoDao eatRepoDao;
	
	@Autowired
	RestaurantDao restaurantDao;
	
	@Autowired
	PriceDao priceDao;
	
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
		
		Price price = new Price();
		price.setName("100");
		priceDao.save(price);
		
		EatRepo eatRepo = new EatRepo();
		eatRepo.setUser(user);
		eatRepo.setEatTitle("title");
		eatRepo.setEatrepo("context");
		eatRepo.setPrice(price);
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
			Price price = eatRepo.getPrice();
			System.out.printf(
					"user=%s,epoId=%d,eatTitle=%s,restaurant=%s,price=%s%n",
					user.getUseraccount(),
					eatRepo.getId(),
					eatRepo.getEatTitle(),
					restaurant.getName(),
					price.getName());
		}
	}
}
