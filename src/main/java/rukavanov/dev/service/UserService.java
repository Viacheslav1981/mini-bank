package rukavanov.dev.service;

import org.springframework.stereotype.Component;
import rukavanov.dev.model.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserService {
    private Map<Integer, User> users = new HashMap<>();
    private int userId;

    public void createUser(String login) {
        User user = new User();
        user.setId(++userId);
        user.setLogin(login);
        users.put(user.getId(), user);
    }

    public User getUserByLogin(String login) {
        return users.values().stream()
                .filter(u -> login.equals(u.getLogin()))
                .findFirst()
                .orElse(null);
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

}
