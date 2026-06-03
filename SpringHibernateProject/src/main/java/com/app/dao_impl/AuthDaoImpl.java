package com.app.dao_impl;

import com.app.dao.AuthDao;
import com.app.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class AuthDaoImpl implements AuthDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User login(String username, String password) {

        User user = em.createQuery("select u from User u where u.username=:username and u.password=:password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
        return user;
    }
}
