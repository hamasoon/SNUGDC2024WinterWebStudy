package core.ref;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());
        for (Field f : clazz.getDeclaredFields())
            logger.debug("Field: " + f.toString());

        for (Constructor c : clazz.getDeclaredConstructors())
            logger.debug("Constructor: " + c.toString());

        for (Method m : clazz.getDeclaredMethods())
            logger.debug("Method: " + m.toString());
    }
    
    @Test
    public void newInstanceWithConstructorArgs() {
        Class<User> clazz = User.class;
        User user;

        try {
            Constructor<User> c = clazz.getDeclaredConstructor(String.class, String.class, String.class, String.class);
            user = c.newInstance("Bae Mun-Sung", "2019-14355", "Bae Mun-Sung", "hamasoon@snu.ac.kr");
            logger.debug(user.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        Student student = new Student();

        for (Field f : clazz.getDeclaredFields()) {
            try  {
                if (f.getName() == "name") {
                    f.setAccessible(true);
                    f.set(student, "Bae Mun-Sung");
                }
                else if (f.getName() == "age") {
                    f.setAccessible(true);
                    f.set(student, 24);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        logger.debug(student.toString());
    }
}
