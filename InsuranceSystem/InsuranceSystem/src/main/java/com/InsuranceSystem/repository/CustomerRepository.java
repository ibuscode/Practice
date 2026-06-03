package com.InsuranceSystem.repository;

import com.InsuranceSystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer getById(int customerId);

    Optional<Customer> findByUserId(int userId);
}
