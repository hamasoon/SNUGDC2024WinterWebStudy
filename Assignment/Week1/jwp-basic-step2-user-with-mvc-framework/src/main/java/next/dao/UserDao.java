package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao {
    private DBConnector connector;
    private Logger log;

    public UserDao() {
        connector = new DBConnector();

        log = LoggerFactory.getLogger("DaoLogger");
    }

    public void insert(User user) throws SQLException {
        try {
            connector.runNonQuery("INSERT INTO USERS VALUES (?, ?, ?, ?)",
                    user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
        } catch (Exception e) {
            log.error("INSERT ERROR", e);
        }
    }

    public void update(User user) throws SQLException {
        try {
            connector.runNonQuery("UPDATE USERS SET (userId, password, name, email)=(?, ?, ?, ?) WHERE userId=?",
                    user.getUserId(), user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
        } catch (Exception e) {
            log.error("UPDATE ERROR", e);
        }
    }

    public List<User> findAll() throws SQLException {
        List<User> result = new ArrayList<>();

        try {
            result = connector.<User>runQuery("SELECT * FROM USERS", User.class);

        } catch (Exception e) {
            log.error("UPDATE ERROR", e);
        }

        return result;
    }

    public User findByUserId(String userId) throws SQLException {
        List<User> list = null;
        User ret = null;

        try {
            list = connector.<User>runQuery("SELECT userId, password, name, email FROM USERS WHERE userid=?", User.class, userId).get(0);
            ret = !list.isEmpty() ? list.get(0) : null;
        } catch (Exception e) {
            log.error("FIND ERROR", e);
        }
        
        return ret;
    }
}
