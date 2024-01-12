package ThinkEat.mvc;

import ThinkEat.mvc.model.entity.*;
import ThinkEat.mvc.dao.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

	@Test
	void createAllData() {
		//建立access1
		Access access1 = new Access();
		access1.setAccessName("ShareOwnEatRepo");
		accessDao.save(access1);

		//建立access2
		Access access2 = new Access();
		access2.setAccessName("EditOwnEatRepo");
		accessDao.save(access2);

		//建立access3
		Access access3 = new Access();
		access3.setAccessName("DeleteOwnEatRepo");
		accessDao.save(access3);

		//建立authority1
		Authority authority1 = new Authority();
		authority1.setAuthorityName("Standard Member");
		authority1.getAccessList().add(access1);
		authority1.getAccessList().add(access2);
		authority1.getAccessList().add(access3);
		authorityDao.save(authority1);

		//建立user1
		User user1 = new User();
		user1.setNickName("Andy");
		user1.setPassword("123");
		user1.setUserName("test1");
		user1.setAuthority(authority1);
		userDao.save(user1);

		//建立user2
		User user2 = new User();
		user2.setNickName("Bobby");
		user2.setPassword("456");
		user2.setUserName("test2");
		user2.setAuthority(authority1);
		userDao.save(user2);

		//建立restaurant1
		Restaurant restaurant1 = new Restaurant();
		restaurant1.setName("麥當勞");
		restaurant1.setAddress("我家巷口");
		restaurantDao.save(restaurant1);

		//建立restaurant2
		Restaurant restaurant2 = new Restaurant();
		restaurant2.setName("頂呱呱");
		restaurant2.setAddress("公館");
		restaurantDao.save(restaurant2);

		//建立restaurant3
		Restaurant restaurant3 = new Restaurant();
		restaurant3.setName("摩斯");
		restaurant3.setAddress("北車");
		restaurantDao.save(restaurant3);

		//建立price1
		Price price1 = new Price();
		price1.setName("100元以內");
		priceDao.save(price1);

		//建立price2
		Price price2 = new Price();
		price2.setName("100~200");
		priceDao.save(price2);

		//建立price3
		Price price3 = new Price();
		price3.setName("200~300");
		priceDao.save(price3);

		//建立price4
		Price price4 = new Price();
		price4.setName("100");
		priceDao.save(price4);

		//建立tag1
		Tag tag1 = new Tag();
		tag1.setName("日式");
		tagDao.save(tag1);

		//建立tag2
		Tag tag2 = new Tag();
		tag2.setName("中式");
		tagDao.save(tag2);

		//建立tag3
		Tag tag3 = new Tag();
		tag3.setName("美式");
		tagDao.save(tag3);

		//建立tag4
		Tag tag4 = new Tag();
		tag4.setName("手搖");
		tagDao.save(tag4);

		//建立tag5
		Tag tag5 = new Tag();
		tag5.setName("會辣");
		tagDao.save(tag5);

		//建立tag6
		Tag tag6 = new Tag();
		tag6.setName("速食");
		tagDao.save(tag6);

		//建立picture1
		Picture picture1 = new Picture();
		picture1.setPath("/pic1");
		prictureDao.save(picture1);

		//建立picture2
		Picture picture2 = new Picture();
		picture2.setPath("/pic2");
		prictureDao.save(picture2);

		//建立eatrepo1
		EatRepo eatRepo1 = new EatRepo();
		eatRepo1.setTitle("好吃");
		eatRepo1.setArticle("真的很好吃");
		eatRepo1.setPrice(price3);
		eatRepo1.getEatRepo_TagList().add(tag6);
		eatRepo1.getEatRepo_TagList().add(tag4);
		eatRepo1.getPicList().add(picture1);
		eatRepoDao.save(eatRepo1);

		//建立eatrepo2
		EatRepo eatRepo2 = new EatRepo();
		eatRepo2.setTitle("難吃");
		eatRepo2.setArticle("真的很難吃");
		eatRepo2.setPrice(price1);
		eatRepo2.getEatRepo_TagList().add(tag1);
		eatRepo2.getEatRepo_TagList().add(tag3);
		eatRepo2.getPicList().add(picture2);
		eatRepoDao.save(eatRepo2);

		//建立comment1
		Comment comment1 =new Comment();
		comment1.setCommentContext("taste very bad");
		comment1.setComment_User(user1);
		comment1.setComment_EatRepo(eatRepo2);
		commentDao.save(comment1);

		//建立comment2
		Comment comment2 =new Comment();
		comment2.setCommentContext("delicious!");
		comment2.setComment_User(user2);
		comment2.setComment_EatRepo(eatRepo1);
		commentDao.save(comment2);

		//建立favlist1
		FavList favList1 = new FavList();
		favList1.setFavListName("我的收藏");
		favList1.setFavList_User(user1);
		favList1.getFavList_EatRepoList().add(eatRepo1);
		favListDao.save(favList1);

		//建立favlist2
		FavList favList2 = new FavList();
		favList2.setFavListName("我的收藏");
		favList2.setFavList_User(user2);
		favList2.getFavList_EatRepoList().add(eatRepo2);
		favListDao.save(favList2);
	}

	//@Test
	//@Transactional
	void queryForAuthority() {
		Authority authority = authorityDao.findById(1).get();

		assertNotNull(authority);
		assertEquals("Standard Member", authority.getAuthorityName());

		List<Access> accessList = authority.getAccessList();
		assertNotNull(accessList);
		assertEquals(3, accessList.size());

		// Output authority details
		System.out.println("Authority: " + authority.getAuthorityName());
		System.out.println("Access List:");
		for (Access access : accessList) {
			System.out.println("- " + access.getAccessName());
		}

		List<User> users = authority.getUserList();
		if (users != null) {
			System.out.println("Users with this authority:");
			for (User user : users) {
				System.out.println("- " + user.getUserName());
			}
		}
	}

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

/*
@Test
	void createPrice() {
		Price price1 = new Price();
		price1.setName("100元以內");
		priceDao.save(price1);

		Price price2 = new Price();
		price2.setName("100~200");
		priceDao.save(price1);

		Price price3 = new Price();
		price3.setName("200~300");
		priceDao.save(price1);

		Price price4 = new Price();
		price4.setName("100");
		priceDao.save(price1);
	}

	//@Test
	void createTag() {
		Tag tag1 = new Tag();
		tag1.setTagName("日式");
		tagDao.save(tag1);

		Tag tag2 = new Tag();
		tag2.setTagName("中式");
		tagDao.save(tag2);

		Tag tag3 = new Tag();
		tag3.setTagName("美式");
		tagDao.save(tag3);

		Tag tag4 = new Tag();
		tag4.setTagName("手搖");
		tagDao.save(tag4);

		Tag tag5 = new Tag();
		tag5.setTagName("會辣");
		tagDao.save(tag5);

		Tag tag6 = new Tag();
		tag6.setTagName("速食");
		tagDao.save(tag6);
	}

	//@Test
	void createEatrepo() {
		EatRepo eatRepo = new EatRepo();
		eatRepo.setEatTitle("title");
		eatRepo.setArticle("context");
		eatRepoDao.save(eatRepo);
	}
 */