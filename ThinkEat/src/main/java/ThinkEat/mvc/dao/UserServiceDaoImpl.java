package ThinkEat.mvc.dao;

import ThinkEat.mvc.model.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class UserServiceDaoImpl implements UserServiceDao {
    private EntityManager entityManager;

    public UserServiceDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByUserName(String username) {
        // retrieve/read from database using username
        TypedQuery<User> theQuery = entityManager
                .createQuery("from User where username =: username", User.class);
        theQuery.setParameter("username", username);
        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            theUser = null;
        }
        return theUser;
    }
}
