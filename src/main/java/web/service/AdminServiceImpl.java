package web.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public void save(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.save(user);
    }

    @Override
    public List<User> findAllUser() {
        return userDao.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        Optional<User> existingUser = userDao.findById(user.getId());
        if (existingUser.isPresent()) {
            User userFromDb = existingUser.get();
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                user.setPassword(userFromDb.getPassword());
            } else if (!user.getPassword().equals(userFromDb.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userDao.update(user);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }
}