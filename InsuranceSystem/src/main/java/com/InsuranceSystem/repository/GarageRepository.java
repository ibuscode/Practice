package com.InsuranceSystem.repository;

import com.InsuranceSystem.enums.GarageStatus;
import com.InsuranceSystem.model.Garage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GarageRepository extends JpaRepository<Garage, Integer> {

    List<Garage> findByStatus(GarageStatus status);

    Page<Garage> findAll(Pageable pageable);

    @Query("SELECT COUNT(g) FROM Garage g WHERE g.status = :status")
    long countByStatus(@Param("status") GarageStatus status);
}
