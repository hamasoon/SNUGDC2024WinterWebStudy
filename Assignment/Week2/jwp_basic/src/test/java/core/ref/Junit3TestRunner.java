package core.ref;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.regex.*;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        String regexPattern = "^test";
        Pattern pattern = Pattern.compile(regexPattern);

        for (Method m : clazz.getDeclaredMethods()){
            Matcher matcher = pattern.matcher(m.getName());
            if(matcher.find())
                m.invoke(clazz.newInstance());
        }
    }
}
