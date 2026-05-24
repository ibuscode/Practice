package com.service;

import com.Exception.RecordNotFoundException;
import com.model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CustomerService {
    private final Session session;

    public CustomerService(Session session) {
        this.session = session;
    }

    public void updatePersonalDetails(int customerId, String customerName, String customerAddress,
                                      int age, long aadhaarNumber, long panNumber) {
        Transaction tx = session.beginTransaction();
        int rows = session.createMutationQuery("""
            update Customer c
            set c.customerName = :customerName,
                c.customerAddress = :customerAddress,
                c.age = :age,
                c.aadhaarNumber = :aadhaarNumber,
                c.panNumber = :panNumber
            where c.customerId = :customerId
            """)
                .setParameter("customerName", customerName)
                .setParameter("customerAddress", customerAddress)
                .setParameter("age", age)
                .setParameter("aadhaarNumber", aadhaarNumber)
                .setParameter("panNumber", panNumber)
                .setParameter("customerId", customerId)
                .executeUpdate();
        tx.commit();

        if (rows == 0) {
            throw new RecordNotFoundException("Invalid Customer ID given!!");
        }


    }
}
