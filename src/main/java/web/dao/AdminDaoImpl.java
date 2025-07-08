package web.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class AdminDaoImpl implements AdminDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> findAllUser() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public List<Role> findAllRole() {
        return entityManager.createQuery("select u from Role u", Role.class).getResultList();
    }

    @Override
    public Optional<User> findByIdUser(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<Role> findByIdRole(Long id) {
        return Optional.ofNullable(entityManager.find(Role.class, id));
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteById(Long id) {
        findByIdUser(id).ifPresent(entityManager::remove);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> users = entityManager.createQuery(
                        "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();

        return users.stream().findFirst();
    }

    @Override
    public Optional<Role> findByName(String name) {
        List<Role> roles = entityManager.createQuery(
                        "SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", name)
                .getResultList();

        return roles.stream().findFirst();
    }

}
