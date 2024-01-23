package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import next.dao.QuestionDao;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

@Controller
@Slf4j
public class HomeController extends AbstractController {
    private QuestionDao questionDao = QuestionDao.getInstance();

    @RequestMapping("/")
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.debug("HomeController Work");
        return jspView("home.jsp").addObject("questions", questionDao.findAll());
    }
}
