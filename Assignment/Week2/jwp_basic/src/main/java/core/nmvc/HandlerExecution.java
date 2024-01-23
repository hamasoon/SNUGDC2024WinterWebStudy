package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class HandlerExecution {
    private Object controller;
    private Method requestMethod;

    public HandlerExecution(Object controller, Method requestMethod) {
        this.controller = controller;
        this.requestMethod = requestMethod;
    }

    @Override
    public String toString() {
        return "HandlerExecution [controller=" + controller.getClass() + " Method=" + requestMethod.getName() + "]";
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            return (ModelAndView) requestMethod.invoke(controller, request, response);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            log.error(requestMethod.getName() + " fail: " + e.toString());
            throw new RuntimeException(e);
        }
    }
}
