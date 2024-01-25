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

    private static FavListService favListService;

    @Autowired
    public SessionListener() {
    }

    public static void setFavListService(FavListService service) {
        favListService = service;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        FavList favList = new FavList();
        favList.setName("訪客預設清單");
        Integer presetFavListId = favListService.addGuestList(favList);

        se.getSession().setAttribute("presetFavListId", presetFavListId);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        Integer presetFavListId = (Integer) se.getSession().getAttribute("presetFavListId");
        if (presetFavListId != null) {
            favListService.deleteFavListById(presetFavListId);
        }

    }
}
