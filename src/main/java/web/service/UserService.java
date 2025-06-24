package web.service;

import web.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
}
