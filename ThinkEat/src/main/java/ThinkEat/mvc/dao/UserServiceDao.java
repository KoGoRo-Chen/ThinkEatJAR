package ThinkEat.mvc.dao;

import ThinkEat.mvc.model.entity.User;

public interface UserServiceDao {
    User findByUserName(String userName);
}
