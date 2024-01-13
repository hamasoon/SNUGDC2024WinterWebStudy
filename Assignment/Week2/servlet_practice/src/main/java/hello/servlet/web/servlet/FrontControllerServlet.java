package hello.servlet.web.servlet;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.servlet.contollers.Controller;
import hello.servlet.web.servlet.contollers.MemberFormController;
import hello.servlet.web.servlet.contollers.MemberListController;
import hello.servlet.web.servlet.contollers.MemberSaveContoller;
import hello.servlet.web.servlet.model.Model;
import hello.servlet.web.servlet.view.Viewer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "FrontController", urlPatterns = "/servlet/*")
public class FrontControllerServlet extends HttpServlet{
	private HashMap<String, Controller> controllerHashMap = new HashMap<>();

	public FrontControllerServlet() {
		controllerHashMap.put("/servlet/members/new-form", new MemberFormController("members/new-form"));
		controllerHashMap.put("/servlet/members", new MemberListController("members"));
		controllerHashMap.put("/servlet/members/save", new MemberSaveContoller("members/save"));
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();

		Controller controller = controllerHashMap.get(uri);
		if(controller == null) {
			response.setStatus(404);
			return;
		}

		HashMap<String, Object> attributes = getAttributeMap(request);
		Model model = controller.run(attributes);

		try {
			Viewer viewer= model.resolveViewer(request);
			viewer.render(request, response);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private HashMap<String, Object> getAttributeMap(HttpServletRequest request) {
		HashMap<String, Object> paramMap = new HashMap<>();

		request.getParameterNames().asIterator()
				.forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

		return paramMap;
	}
}