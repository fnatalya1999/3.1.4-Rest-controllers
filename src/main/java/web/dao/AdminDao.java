package web.dao;

import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Optional;

public interface AdminDao {
    void save(User user);
    List<User> findAllUser();
    List<Role> findAllRole();
    Optional<User> findByIdUser(Long id);
    Optional<Role> findByIdRole(Long id);
    void updateUser(User user);
    void deleteById(Long id);
    Optional<User> findByEmail(String email);
    Optional<Role> findByName(String name);
}
