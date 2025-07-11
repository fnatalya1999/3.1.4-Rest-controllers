package web.service;

import web.model.User;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    void save(User user);
    List<User> findAllUser();
    Optional<User> findById(Long id);
    void updateUser(User user);
    void deleteById(Long id);
}
