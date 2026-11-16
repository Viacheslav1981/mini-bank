package rukavanov.dev.service;

import org.springframework.stereotype.Service;
import rukavanov.dev.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final Map<Integer, User> users = new HashMap<>();
    private int userId;

    public void addUser(String login) {
        User user = new User(++userId, login);
        users.put(user.getId(), user);
    }

    public User getUserByLogin(String login) {
        return users.values().stream()
                .filter(u -> login.equals(u.getLogin()))
                .findFirst()
                .orElse(null);
    }

    public User getUserById(int userId) {
        return users.get(userId);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

}
