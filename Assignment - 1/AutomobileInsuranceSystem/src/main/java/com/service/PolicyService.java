package com.service;

import com.Exception.RecordNotFoundException;
import com.enums.StatusType;
import com.model.Policy;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PolicyService {
    private final Session session;

    public PolicyService (Session session) {
        this.session = session;
    }
    public void insert(Policy policy) {
        Transaction tx = session.beginTransaction();
        session.persist(policy);
        tx.commit();

    }

    public void deleteById(int id) {
        Transaction tx = session.beginTransaction();
        Policy policy = session.find(Policy.class, id);
        if(policy==null) {
            tx.commit();
            throw new RecordNotFoundException("Invalid ID given!!");
        }
        session.createMutationQuery("delete from Policy where policyId = :policyId")
        .setParameter("policyId", id)
                .executeUpdate();
        tx.commit();
    }

    public List<Policy> fetch(String username) {
        Transaction tx = session.beginTransaction();
        List<Policy> list = session.createQuery("FROM Policy p WHERE p.customer.user.userName = :username", Policy.class)
                .setParameter("username", username)
                .list();
        tx.commit();
        return list;
    }

    public Policy fetchById(int id) {
        Transaction tx = session.beginTransaction();
        Policy policy = session.find(Policy.class, id);
        tx.commit();

        if(policy==null) {
            throw new RecordNotFoundException("Invalid ID given!!");
        }

        return policy;
    }

    public void updateCustomerStatus(int policyId, StatusType statusType) {
        Transaction tx = session.beginTransaction();
        Policy policy = session.find(Policy.class, policyId);

        if(policy==null) {
            tx.commit();
            throw new RecordNotFoundException("Invalid Policy ID given!!");
        }

        policy.setStatusType(statusType);
        session.merge(policy);
        tx.commit();
    }
}
