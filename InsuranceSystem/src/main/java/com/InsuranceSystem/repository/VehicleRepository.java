package com.InsuranceSystem.repository;

import com.InsuranceSystem.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {


    List<Vehicle> findByCustomerCustomerId(int customerId);

    Optional<Vehicle> findByRegNo(String regNo);


}
