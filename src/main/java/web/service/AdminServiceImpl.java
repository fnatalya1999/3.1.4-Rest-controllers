package web.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.AdminDao;
import web.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminDao adminDao;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminDao adminDao, PasswordEncoder passwordEncoder) {
        this.adminDao = adminDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public void save(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        adminDao.save(user);
    }

    @Override
    public List<User> findAllUser() {
        return adminDao.findAllUser();
    }

    @Override
    public Optional<User> findById(Long id) {
        return adminDao.findByIdUser(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        Optional<User> existingUser = adminDao.findByIdUser(user.getId());
        if (existingUser.isPresent()) {
            User userFromDb = existingUser.get();
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                user.setPassword(userFromDb.getPassword());
            } else if (!user.getPassword().equals(userFromDb.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            adminDao.updateUser(user);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        adminDao.deleteById(id);
    }
}