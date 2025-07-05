package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.dao.AdminDaoImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminDaoImpl adminDao;

    @Autowired
    public UserDetailsServiceImpl(AdminDaoImpl adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        web.model.User user = adminDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getName()))
                        .toList()
        );
    }
}
