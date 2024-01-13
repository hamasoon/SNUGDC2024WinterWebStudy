package hello.servlet.web.servlet.contollers;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.servlet.model.Model;
import hello.servlet.web.servlet.view.Viewer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@WebServlet(name="memberListServlet", urlPatterns = "/servlet/members")
public class MemberListController extends Controller{
    public MemberListController(String viewPath) {
        super(viewPath);
    }

    @Override
    public Model run(HashMap<String, Object> attributes) {
        List<Member> members = memberRepository.findAll();
        HashMap<String, Object> model = new HashMap<>();
        model.put("Members", members);
        return new Model(viewPath, model);
    }
}
