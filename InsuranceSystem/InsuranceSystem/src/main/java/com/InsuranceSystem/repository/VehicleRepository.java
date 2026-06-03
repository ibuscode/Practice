package com.InsuranceSystem.repository;

import com.InsuranceSystem.dto.VehicleResDto;
import com.InsuranceSystem.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    @Query(
            """
select v from Vehicle v where v.customer.customerId=?1
"""
    )
List<Vehicle> getById(int customerId);
}
