package next.controller.user;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.nmvc.AbstractNewController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import next.controller.UserSessionUtils;
import next.dao.UserDao;

@Controller
@Slf4j
public class UserController extends AbstractNewController {
    private UserDao userDao = UserDao.getInstance();

    @RequestMapping("/users")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        ModelAndView mav = jspView("/user/list.jsp");
        mav.addObject("users", userDao.findAll());
        return mav;
    }

}
