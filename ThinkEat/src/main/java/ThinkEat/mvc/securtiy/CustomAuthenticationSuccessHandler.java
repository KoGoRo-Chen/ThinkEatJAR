package ThinkEat.mvc.securtiy;

import ThinkEat.mvc.dao.UserServiceDao;
import ThinkEat.mvc.model.entity.User;
import ThinkEat.mvc.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private UserService userService;
    private UserServiceDao userServiceDao;

    public CustomAuthenticationSuccessHandler(UserService userService,
                                              UserServiceDao userServiceDao) {
        this.userServiceDao = userServiceDao;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("In customAuthenticationSuccessHandler");

        String userName = authentication.getName();

        System.out.println("userName=" + userName);

        User theUser = userServiceDao.findByUserName(userName);

        if (theUser != null) {
            // now place in the session
            HttpSession session = request.getSession();
            session.setAttribute("user", theUser);

            // forward to home page
            response.sendRedirect("/ThinkEat/Index");
        } else {
            // handle the case where the user is not found
            response.sendRedirect("ThinkEat/AccessDenied");
        }
    }
}
