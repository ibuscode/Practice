package com.app.dao_impl;

import com.app.dao.PolicyDao;
import com.app.enums.StatusType;
import com.app.model.Policy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class PolicyDaoImpl implements PolicyDao {
    private final JdbcTemplate jdbcTemplate;

    public PolicyDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addPolicy(Policy policy) {
        String sql = "insert into policy (policy_number, status_type, proposal_date, start_date, end_date, premium_amount) values (?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                policy.getPolicyNumber(),
                policy.getStatusType().name(),
                Timestamp.from(policy.getProposalDate()),
                Date.valueOf(policy.getStartDate()),
                Date.valueOf(policy.getEndDate()),
                policy.getPremiumAmount()
        );
    }

    @Override
    public int deletePolicyById(int policyId) {
        String sql = "delete from policy where policy_id = ?";
        return jdbcTemplate.update(sql, policyId);
    }

    @Override
    public int updatePolicy(Policy policy) {
        String sql = "update policy set policy_number = ?, status_type = ?, start_date = ?, end_date = ?, premium_amount = ? where policy_id = ?";

        return jdbcTemplate.update(
                sql,
                policy.getPolicyNumber(),
                policy.getStatusType().name(),
                Date.valueOf(policy.getStartDate()),
                Date.valueOf(policy.getEndDate()),
                policy.getPremiumAmount(),
                policy.getPolicyId()
        );
    }

    @Override
    public List<Policy> getAllPolicies() {
        String sql = "select policy_id, policy_number, status_type, proposal_date, start_date, end_date, premium_amount from policy";
        return jdbcTemplate.query(sql, new PolicyRowMapper());
    }

    @Override
    public Policy getPolicyById(int policyId) {
        String sql = "select policy_id, policy_number, status_type, proposal_date, start_date, end_date, premium_amount from policy where policy_id = ?";
        return jdbcTemplate.queryForObject(sql, new PolicyRowMapper(), policyId);
    }

    private static class PolicyRowMapper implements RowMapper<Policy> {
        @Override
        public Policy mapRow(ResultSet rs, int rowNum) throws SQLException {
            Policy policy = new Policy();
            policy.setPolicyId(rs.getInt("policy_id"));
            policy.setPolicyNumber(rs.getString("policy_number"));
            policy.setStatusType(StatusType.valueOf(rs.getString("status_type")));
            policy.setProposalDate(rs.getTimestamp("proposal_date").toInstant());
            policy.setStartDate(rs.getDate("start_date").toLocalDate());
            policy.setEndDate(rs.getDate("end_date").toLocalDate());
            policy.setPremiumAmount(rs.getFloat("premium_amount"));
            return policy;
        }
    }
}
