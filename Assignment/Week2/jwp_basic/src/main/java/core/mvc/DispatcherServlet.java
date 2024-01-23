package core.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.RequestMapping;
import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private enum MappingType {
        ANNOTATION, LEGACY;
    }
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<MappingType, HandlerMapping> handlerMappingMap;

    @Override
    public void init() throws ServletException {
        OldHandlerMapping oldHandlerMapping = new OldHandlerMapping();
        oldHandlerMapping.initMapping();
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping();
        annotationHandlerMapping.initMapping();

        handlerMappingMap = new HashMap<>();
        handlerMappingMap.put(MappingType.LEGACY, oldHandlerMapping);
        handlerMappingMap.put(MappingType.ANNOTATION, annotationHandlerMapping);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);
        Object handler = null;

        for (HandlerMapping handlerMapping : handlerMappingMap.values()) {
            handler = handlerMapping.getHandler(req);
            if (handler != null) break;
        }

        if (handler == null) throw new ServletException("Not exist URL");

        ModelAndView mav;
        try {
            if (handler instanceof HandlerExecution) {
                logger.debug("Use Annotation");
                logger.debug("Method: " + ((HandlerExecution) handler).toString());
                mav = ((HandlerExecution) handler).handle(req, resp);
            }
            else {
                logger.debug("Use Legacy");
                mav = ((Controller) handler).execute(req, resp);
            }
            View view = mav.getView();
            view.render(mav.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }
}
