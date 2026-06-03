package com.app.dao;

import com.app.model.Policy;

import java.util.List;

public interface PolicyDao {
    int addPolicy(Policy policy);

    int deletePolicyById(int policyId);

    int updatePolicy(Policy policy);

    List<Policy> getAllPolicies();

    Policy getPolicyById(int policyId);
}
