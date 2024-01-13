package hello.servlet.web.servlet.contollers;

import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.servlet.model.Model;
import hello.servlet.web.servlet.view.Viewer;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberFormController extends Controller {
    public MemberFormController(String viewPath) { super(viewPath); }

    public Model run(HashMap<String, Object> attributes) {
        return new Model(viewPath);
    }
}
