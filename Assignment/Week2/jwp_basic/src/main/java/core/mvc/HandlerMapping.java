package core.mvc;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
	 public void initMapping();
	 public Object getHandler(HttpServletRequest request);
}
