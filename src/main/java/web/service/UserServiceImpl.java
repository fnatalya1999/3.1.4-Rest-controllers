package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.AdminDaoImpl;
import web.model.User;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final AdminDaoImpl adminDao;
    @Autowired
    public UserServiceImpl(AdminDaoImpl adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return adminDao.findByEmail(email);
    }
}
