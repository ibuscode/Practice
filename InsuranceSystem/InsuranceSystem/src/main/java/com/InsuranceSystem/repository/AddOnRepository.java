package com.InsuranceSystem.repository;

import com.InsuranceSystem.model.AddOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddOnRepository extends JpaRepository<AddOn, Integer> {

    List<AddOn> findByPolicyTypeId(int policyTypeId);
}
