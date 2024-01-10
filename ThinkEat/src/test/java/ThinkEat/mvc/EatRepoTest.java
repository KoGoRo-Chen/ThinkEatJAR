package ThinkEat.mvc;

import org.apache.naming.java.javaURLContextFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ThinkEat.mvc.dao.jpa.EatRepoDao;
import ThinkEat.mvc.dao.jpa.UserDao;
import ThinkEat.mvc.entity.jpa.EatRepo;
import ThinkEat.mvc.entity.jpa.User;
import jakarta.transaction.Transactional;

@SpringBootTest
public class EatRepoTest {

	@Autowired
	UserDao userDao;
	
	@Autowired
	EatRepoDao eatRepoDao;	
	
	//@Test
	void createUser() {
		
		User user = new User();
		
		user.setLevelId(2);
		user.setNickName("nickname");
		user.setPassword("123");
		user.setUseraccount("account");
		
		userDao.save(user);
		
	}
	
	//@Test
	void addEatRepo() {
		
		User user = userDao.findById(1).get();
		
		EatRepo eatRepo = new EatRepo();
		eatRepo.setUser(user);
		eatRepo.setEatTitle("title");
		eatRepo.setEatrepo("context");
		eatRepo.setPriceId(1);
		eatRepo.setResId(1);
		
		eatRepoDao.save(eatRepo);
		
	}
	
	@Test
	@Transactional
	void queryUser() {
		User user = userDao.findById(1).get();
		System.out.println(user);
	}
	
}
