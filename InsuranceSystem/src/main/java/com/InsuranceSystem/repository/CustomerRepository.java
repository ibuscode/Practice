package com.InsuranceSystem.repository;

import com.InsuranceSystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer getById(int customerId);

    Optional<Customer> findByUserId(int userId);

    Optional<Customer> findByUserUsername(String username);

    @Query("SELECT c FROM Customer c JOIN FETCH c.user u WHERE u.username = :username")
    Optional<Customer> findCustomerWithUserByUsername(@Param("username") String username);


}
