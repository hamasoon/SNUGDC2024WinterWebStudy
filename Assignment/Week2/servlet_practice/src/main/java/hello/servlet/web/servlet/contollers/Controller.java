package hello.servlet.web.servlet.contollers;

import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.servlet.model.Model;
import hello.servlet.web.servlet.view.Viewer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public abstract class Controller {
	protected MemberRepository memberRepository;
	protected final String viewPath;

	public Controller(String viewPath) {
		this.memberRepository = MemberRepository.getInstance();
		this.viewPath = viewPath;
	}

	public abstract Model run(HashMap<String, Object> attributes);
}
