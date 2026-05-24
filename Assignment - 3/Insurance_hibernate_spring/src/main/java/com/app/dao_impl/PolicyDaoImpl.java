package com.app.dao_impl;

import com.app.Exceptions.RecordNotFoundException;
import com.app.dao.PolicyDao;
import com.app.enums.StatusType;
import com.app.enums.VehicleType;
import com.app.model.Customer;
import com.app.model.Policy;
import com.app.model.User;
import com.app.model.Vehicle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class PolicyDaoImpl implements PolicyDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void applyForPolicy(int userId, int vehicleId, String policyNumber, float premiumAmount) {
        Customer customer = getCustomerByUserId(userId);

        List<Vehicle> vehicles = em.createQuery(
                        "select v from Vehicle v where v.vehicleId=:vehicleId and v.customer.customerId=:customerId",
                        Vehicle.class)
                .setParameter("vehicleId", vehicleId)
                .setParameter("customerId", customer.getCustomerId())
                .getResultList();

        if (vehicles.isEmpty()) {
            throw new RecordNotFoundException("Vehicle not found for this customer");
        }

        Policy policy = new Policy();
        policy.setPolicyNumber(policyNumber);
        policy.setStatusType(StatusType.PROPOSAL_SUBMITTED);
        policy.setPremiumAmount(premiumAmount);
        policy.setCustomer(customer);
        policy.setVehicle(vehicles.get(0));

        em.persist(policy);
    }

    @Override
    @Transactional
    public void addVehicle(int userId, String regNo, VehicleType vehicleType) {
        Customer customer = getCustomerByUserId(userId);

        Vehicle vehicle = new Vehicle();
        vehicle.setRegNo(regNo);
        vehicle.setVehicleType(vehicleType);
        vehicle.setCustomer(customer);

        em.persist(vehicle);
    }

    @Override
    @Transactional
    public void updatePersonalInfo(int userId, String name, String address, int age, long aadhaarNumber, long panNumber) {
        int count = em.createQuery("update Customer c set c.customerName=:name, c.customerAddress=:address, c.age=:age, c.aadhaarNumber=:aadhaarNumber, c.panNumber=:panNumber where c.user.id=:userId")
                .setParameter("name", name)
                .setParameter("address", address)
                .setParameter("age", age)
                .setParameter("aadhaarNumber", aadhaarNumber)
                .setParameter("panNumber", panNumber)
                .setParameter("userId", userId)
                .executeUpdate();

        if (count == 0) {
            throw new RecordNotFoundException("Customer not found");
        }
    }

    @Override
    @Transactional
    public void cancelPolicy(int userId, int policyId) {
        int count = em.createQuery("update Policy p set p.statusType=:status where p.policyId=:policyId and p.customer.user.id=:userId")
                .setParameter("status", StatusType.EXPIRED)
                .setParameter("policyId", policyId)
                .setParameter("userId", userId)
                .executeUpdate();

        if (count == 0) {
            throw new RecordNotFoundException("Policy not found for this customer");
        }
    }

    @Override
    public List<Policy> viewCustomerPolicies(int userId) {
        return em.createQuery("select p from Policy p where p.customer.user.id=:userId", Policy.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Policy> viewOfficerPolicies(int userId) {
        return em.createQuery("select p from Policy p where p.officer.user.id=:userId", Policy.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Policy> viewPolicyByCustomer(int customerId) {
        return em.createQuery("select p from Policy p where p.customer.customerId=:customerId", Policy.class)
                .setParameter("customerId", customerId)
                .getResultList();
    }

    @Override
    public List<Policy> filterPolicyByStatus(StatusType statusType) {
        return em.createQuery("select p from Policy p where p.statusType=:statusType", Policy.class)
                .setParameter("statusType", statusType)
                .getResultList();
    }

    @Override
    @Transactional
    public void updatePolicyStatusAndDate(int policyId, StatusType statusType, LocalDate startDate, LocalDate endDate) {
        int count = em.createQuery("update Policy p set p.statusType=:statusType, p.startDate=:startDate, p.endDate=:endDate where p.policyId=:policyId")
                .setParameter("statusType", statusType)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("policyId", policyId)
                .executeUpdate();

        if (count == 0) {
            throw new RecordNotFoundException("Policy not found");
        }
    }

    @Override
    public List<User> viewAllUsers() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public List<Policy> viewAllPolicies() {
        return em.createQuery("select p from Policy p", Policy.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void deleteUser(int userId) {
        int count = em.createQuery("delete from User u where u.id=:userId")
                .setParameter("userId", userId)
                .executeUpdate();

        if (count == 0) {
            throw new RecordNotFoundException("User not found");
        }
    }

    private Customer getCustomerByUserId(int userId) {
        List<Customer> customers = em.createQuery("select c from Customer c where c.user.id=:userId", Customer.class)
                .setParameter("userId", userId)
                .getResultList();

        if (customers.isEmpty()) {
            throw new RecordNotFoundException("Customer not found");
        }

        return customers.get(0);
    }
}
