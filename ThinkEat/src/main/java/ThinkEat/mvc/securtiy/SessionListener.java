package ThinkEat.mvc.securtiy;

import ThinkEat.mvc.dao.FavListDao;
import ThinkEat.mvc.dao.RestaurantDao;
import ThinkEat.mvc.model.entity.FavList;
import ThinkEat.mvc.service.EatRepoService;
import ThinkEat.mvc.service.FavListService;
import ThinkEat.mvc.service.RestaurantService;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
