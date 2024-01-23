package core.nmvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;

import com.google.common.collect.Sets;
import core.annotation.RequestMethod;
import core.annotation.RequestMapping;
import core.mvc.HandlerMapping;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Set;

@Slf4j
public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
    private ControllerScanner scanner;

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
        scanner = new ControllerScanner();
    }

    public void initMapping() {
        Map<Class<?>, Object> controllers = scanner.getController();
        Set<Method> methods = getAllMethod(controllers.keySet());

        for(Method m : methods) {
            RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
            handlerExecutions.put(new HandlerKey(requestMapping.value(), requestMapping.method()),
              new HandlerExecution(controllers.get(m.getDeclaringClass()), m));
        }
    }

    @SuppressWarnings("unchecked")
    private Set<Method> getAllMethod(Set<Class<?>> controllers) {
        Set<Method> methods = Sets.newHashSet();

        for (Class<?> c : controllers) {
            for(Method m : c.getDeclaredMethods()) {
                if (m.isAnnotationPresent(RequestMapping.class)){
                    log.debug("Controller: " + c.getName() + " Method: " + m.getName() + " Mapped");
                    methods.add(m);
                }
            }
        }

        return methods;
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod().toUpperCase())));
    }
}
