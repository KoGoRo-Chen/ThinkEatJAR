package ThinkEat.mvc;

import java.util.List;

import ThinkEat.mvc.Jpa.Dao.*;
import ThinkEat.mvc.Jpa.Entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;

@SpringBootTest
public class SpringDaoTest {

	@Autowired
	UserDao userDao;

	@Autowired
	AuthorityDao authorityDao;

	@Autowired
	AccessDao accessDao;

	@Autowired
	FavListDao favListDao;

	@Autowired
	RestaurantDao restaurantDao;

	@Autowired
	EatRepoDao eatRepoDao;

	@Autowired
	FavList_EatRepoDao favList_eatRepoDao;

	@Autowired
	PriceDao priceDao;

	@Autowired
	TagDao tagDao;

	@Autowired
	EatRepo_TagDao eatRepo_tagDao;

	@Autowired
	PictureDao prictureDao;

	@Autowired
	CommentDao commentDao;

	//@Test
	void createAcess1() {
		Access access1 = new Access();
		access1.setAccessName("ShareOwnEatRepo");
		accessDao.save(access1);
	}

	//@Test
	void createAcess2() {
		Access access2 = new Access();
		access2.setAccessName("EditOwnEatRepo");
		accessDao.save(access2);
	}

	//@Test
	void createAcess3() {
		Access access3 = new Access();
		access3.setAccessName("DeleteOwnEatRepo");
		accessDao.save(access3);
	}

	//@Test
	void createAuthority() {
		Authority authority = new Authority();
		authority.setId(1);
		authority.setAuthorityName("Standard Member");
	}


	@Test
	void createUser() {
		User user = new User();
		user.setNickName("nickname");
		user.setPassword("123");
		user.setUserName("account");
		userDao.save(user);
	}

	//@Test
	void createRestauarnt() {
		Restaurant restaurant = new Restaurant();
		restaurant.setName("麥當勞");
		restaurant.setAddress("台北市建國路一段456號");
		restaurantDao.save(restaurant);
	}

	//@Test
	void createPrice() {
		Price price = new Price();
		price.setName("100");
		priceDao.save(price);
	}

	//@Test
	void createTag() {

	}

	//@Test
	void createEatrepo() {
		EatRepo eatRepo = new EatRepo();
		eatRepo.setEatTitle("title");
		eatRepo.setArticle("context");
		eatRepoDao.save(eatRepo);
	}

	//@Test
	void createPhoto() {

	}

	//@Test
	void createComment() {

	}

	//@Test
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
