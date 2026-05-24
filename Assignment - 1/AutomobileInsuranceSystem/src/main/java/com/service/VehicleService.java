package com.service;

import com.Exception.RecordNotFoundException;
import com.enums.VehicleType;
import com.model.Customer;
import com.model.Vehicle;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class VehicleService {
    private final Session session;

    public VehicleService(Session session) {
        this.session = session;
    }

    public void addVehicle(int customerId, String regNo, VehicleType vehicleType) {
        Transaction tx = session.beginTransaction();
        Customer customer = session.find(Customer.class, customerId);

        if (customer == null) {
            tx.commit();
            throw new RecordNotFoundException("Invalid Customer ID given!!");
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setRegNo(regNo);
        vehicle.setVehicleType(vehicleType);
        vehicle.setCustomer(customer);

        session.persist(vehicle);
        tx.commit();
    }
}
