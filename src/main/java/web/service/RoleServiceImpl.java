package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.AdminDaoImpl;
import web.model.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final AdminDaoImpl adminDao;

    @Autowired
    public RoleServiceImpl(AdminDaoImpl adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public List<Role> findAllRole() {
        return adminDao.findAllRole();
    }

    @Override
    public Role findById(Long id) {
        return adminDao.findByIdRole(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }
}
