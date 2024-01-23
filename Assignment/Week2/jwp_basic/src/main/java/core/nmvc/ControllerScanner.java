package core.nmvc;

import core.annotation.Controller;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Constructor;


@Slf4j
public class ControllerScanner {
	private Reflections reflections;
	private Map<Class<?>, Object> controllers;

	public ControllerScanner() {
	}

	private void initializeMap() {
		controllers = new HashMap<>();
		reflections = new Reflections();
		log.debug("Controller Annotated Founded: " + reflections.getTypesAnnotatedWith(Controller.class).size());

		try {
			for(Class<?> c : reflections.getTypesAnnotatedWith(Controller.class)){
				controllers.put(c, c.newInstance());
			}
		}
		catch (IllegalAccessException | InstantiationException e) {
			log.error(e.getMessage());
		}

		log.debug("Controller Founded: " + controllers.size());
	}

	public Map<Class<?>, Object> getController() {
		initializeMap();
		return controllers;
	}
}
