package ThinkEat.mvc.dao;

import ThinkEat.mvc.bean.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserBeanDaoImplInMemory implements UserBeanDao{
	// in Memory資料庫
	private static List<User> usersSum = new CopyOnWriteArrayList<User>();
	private static AtomicInteger atomicUserId = new AtomicInteger(0);  //使用者ID
	
	@Autowired
	@Qualifier("eatDataDaoImplInMemory")
	private EatDataDao eatDataDao;
	
	@Autowired
	@Qualifier("shareEatDaoImplInMemory")
	private ShareEatDao shareEatDao;
	
    // 構造函數
    public UserBeanDaoImplInMemory() {
        initializeDefaultUser();
    }

    // 初始化預設使用者
    @PostConstruct
    private void initializeDefaultUser() {
        User defaultUser = new User();
        defaultUser.setUserId(atomicUserId.incrementAndGet());
        defaultUser.setUserNickName("admin");
        defaultUser.setUsername("admin556");
        defaultUser.setPassword("admin123");

        // 將預設使用者添加到資料庫
        usersSum.add(defaultUser);
    }
	
	//查詢所有使用者(多筆)
	@Override
	public List<User> findAllUsers() {
		return usersSum;
	}

	//使用者註冊
	@Override
	public void addUser(User user) {
		int userId = atomicUserId.incrementAndGet();
		user.setUserId(userId);
		usersSum.add(user);
	}

	//修改密碼
	@Override
	public Boolean updateUserPassword(Integer userId, String newPassword) {
		Optional<User> userTUP = findUserById(userId);
		 if (userTUP.isPresent()) {
	            User user = userTUP.get();
	            user.setPassword(newPassword);
	            System.out.println("使用者密碼更新成功！");
	            return true; 
	        }
	            return false; 
	    }
	
	//透過使用者ID尋找密碼
	@Override
	public String findUserPasswordById(Integer userId) {
		String curPassWord = findUserById(userId).get().getPassword();
		return curPassWord;
	}
	
	//根據使用者名稱查找使用者(登入用-單筆)
	@Override
	public Optional<User> findUserByUsername(String username) {
		Optional<User> userOpt = usersSum.stream().filter(u -> u.getUsername().equals(username)).findFirst();
		return userOpt;
	}
	
	//根據使用者Id查找使用者
	@Override
	public Optional<User> findUserById(Integer userId) {
		Optional<User> userOpt = usersSum.stream().filter(u -> u.getUserId().equals(userId)).findFirst();
		return userOpt;
	}
	
		

}
