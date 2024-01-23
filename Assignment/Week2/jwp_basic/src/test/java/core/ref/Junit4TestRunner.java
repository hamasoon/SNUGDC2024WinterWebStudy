package core.ref;

import org.junit.Test;

import java.lang.reflect.Method;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;

        for(Method m : clazz.getDeclaredMethods()) {
            if(m.isAnnotationPresent(MyTest.class)) {
                m.invoke(clazz.newInstance());
            }
        }
    }
}
