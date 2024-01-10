package ThinkEat.mvc.Bean.Dao;

import ThinkEat.mvc.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserBeanDao {
//	使用者-User:
//	查詢所有使用者(多筆)
	List<User> findAllUsers();
	
//	新增使用者
	void addUser(User user);
	
//	修改密碼
	Boolean updateUserPassword(Integer userId, String newPassword);
	
//	根據使用者名稱查找使用者(登入用-單筆)
	Optional<User> findUserByUsername(String username);
	
//	根據使用者ID查找使用者(單筆)
	Optional<User> findUserById(Integer userId);

// 根據使用者ID尋找密碼
	String findUserPasswordById(Integer userId);
}
