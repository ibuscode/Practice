package com.service;

import com.Exception.RecordNotFoundException;
import com.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AuthService {

    private final Session session;

    public AuthService(Session session) {
        this.session = session;
    }

    public User login(String userName, String password) {
        Transaction tx = session.beginTransaction();
        User user = session.createQuery("from User where userName = :userName and password = :password", User.class)
                        .setParameter("userName", userName)
                                .setParameter("password", password)
                                        .getSingleResult();
        tx.commit();
        if(user != null) {
            return user;
        }
        else {
            throw new RecordNotFoundException("Invalid Credentials!!");
        }

    }
}
