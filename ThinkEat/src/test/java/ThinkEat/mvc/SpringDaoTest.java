package ThinkEat.mvc;

import java.util.List;

import ThinkEat.mvc.Jpa.Entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ThinkEat.mvc.Jpa.Dao.EatRepoDao;
import ThinkEat.mvc.Jpa.Dao.PriceDao;
import ThinkEat.mvc.Jpa.Dao.RestaurantDao;
import ThinkEat.mvc.Jpa.Dao.UserDao;
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
	void createAcess() {
		Authority authority = new Authority();
		authority.setId(1);
		authority.setAuthorityName("Standard Member");
	}

	void createAuthority() {
		Authority authority = new Authority();
		authority.setId(1);
		authority.setAuthorityName("Standard Member");
	}

	void createUser() {
		User user = new User();
		user.setNickName("nickname");
		user.setPassword("123");
		user.setUserName("account");
		userDao.save(user);
	}

	void createRestauarnt() {
		Restaurant restaurant = new Restaurant();
		restaurant.setName("麥當勞");
		restaurant.setAddress("台北市建國路一段456號");
		restaurantDao.save(restaurant);
	}

	void createPrice() {
		Price price = new Price();
		price.setName("100");
		priceDao.save(price);
	}

	void createTag() {

	}

	void createEatrepo() {
		EatRepo eatRepo = new EatRepo();
		eatRepo.setEatTitle("title");
		eatRepo.setArticle("context");
		eatRepoDao.save(eatRepo);
	}

	void createPhoto() {

	}

	void createComment() {

	}

	void createFavList() {

	}

	/*
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
	*/
}
