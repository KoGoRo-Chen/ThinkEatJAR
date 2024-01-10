package ThinkEat.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ThinkEat.mvc.dao.demo.ArticleDao;
import ThinkEat.mvc.dao.demo.EmployeeDao;
import ThinkEat.mvc.entity.demo.Article;
import ThinkEat.mvc.entity.demo.Employee;

@SpringBootTest
class ThinkEatApplicationTests {

	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	ArticleDao articleDao;
	
	@Test
	void test1() {
		
		Employee employee = new Employee();
		employee.setUsername("aweit-zhu");
		employee.setPassword("123");
		employee.setUserNickName("aweit");
		
		employeeDao.save(employee);
		
		System.out.println(employee.getUserId());

	}
	
	@Test
	void test2() {
		
		Employee e1 = employeeDao.findById(1).get();
		
		Article a1 = new Article();
		a1.setTitle("title1");
		a1.setEmployee(e1);
		articleDao.save(a1);
		
		Article a2 = new Article();
		a2.setTitle("title2");
		a2.setEmployee(e1);
		articleDao.save(a2);

	}
	
	//@Test
	//@Transactional
	void test3() {
		
		Employee employee = employeeDao.findById(1).get();

		System.out.println(employee);

	}

}
