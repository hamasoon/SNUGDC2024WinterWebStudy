package hello.servlet.web.servlet.contollers;

import hello.servlet.domain.member.Member;
import hello.servlet.web.servlet.model.Model;
import hello.servlet.web.servlet.view.Viewer;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name="memberSaveServlet", urlPatterns = "/servlet/members/save")
public class MemberSaveContoller extends Controller{

    public MemberSaveContoller(String viewPath) {
        super(viewPath);
    }

    @Override
    public Model run(HashMap<String, Object> attributes) {
        System.out.println("MemberSaveServlet.service");
        String username = attributes.get("username").toString(); // 파라미터를 꺼내서
        int age = Integer.parseInt(attributes.get("age").toString()); // 파라미터를 꺼내서
        Member member = memberRepository.save(new Member(username, age));

        HashMap<String, Object> model = new HashMap<>();
        model.put("Member", member); // 저장 및 전송

        return new Model(viewPath, model);
    }
}
