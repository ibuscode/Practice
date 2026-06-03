package com.app.dao;

import com.app.enums.StatusType;
import com.app.enums.VehicleType;
import com.app.model.Policy;
import com.app.model.User;

import java.time.LocalDate;
import java.util.List;

public interface PolicyDao {

    void applyForPolicy(int userId, int vehicleId, String policyNumber, float premiumAmount);

    void addVehicle(int userId, String regNo, VehicleType vehicleType);

    void updatePersonalInfo(int userId, String name, String address, int age, long aadhaarNumber, long panNumber);

    void cancelPolicy(int userId, int policyId);

    List<Policy> viewCustomerPolicies(int userId);

    List<Policy> viewOfficerPolicies(int userId);

    List<Policy> viewPolicyByCustomer(int customerId);

    List<Policy> filterPolicyByStatus(StatusType statusType);

    void updatePolicyStatusAndDate(int policyId, StatusType statusType, LocalDate startDate, LocalDate endDate);

    List<User> viewAllUsers();

    List<Policy> viewAllPolicies();

    void deleteUser(int userId);
}
