package hello.servlet.web.servlet.model;

import hello.servlet.web.servlet.view.Viewer;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.HashMap;

public class Model {
	private final String viewPath;
	private HashMap<String, Object> model = new HashMap<>();

	public Model(String viewPath, HashMap<String, Object> model) {
		this.viewPath = viewPath;
		this.model = model;
	}

	public Model(String viewPath) {
		this.viewPath = viewPath;
		this.model = null;
	}

	public Viewer resolveViewer(HttpServletRequest request) {
		if(model != null) model.forEach((key, value) -> request.setAttribute(key, value));

		return new Viewer("/jsp/" + viewPath + ".jsp");
	}
}
