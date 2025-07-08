package web.dao;

import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void save(User user);
    void update(User user);
    void deleteById(Long id);
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
}
