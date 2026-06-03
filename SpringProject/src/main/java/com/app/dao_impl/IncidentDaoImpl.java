package com.app.dao_impl;

import com.app.dao.IncidentDao;
import com.app.enums.IncidentStatus;
import com.app.enums.IncidentType;
import com.app.exceptions.ResourceNotFoundException;
import com.app.model.Incident;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncidentDaoImpl implements IncidentDao {

    private final JdbcTemplate jdbcTemplate;

    public IncidentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public RowMapper<Incident> mapper() {
        return (rs, num) -> {
            return new Incident(rs.getInt("id"),
                    IncidentType.valueOf(rs.getString("type")),
                    rs.getString("details"),
                    IncidentStatus.valueOf(rs.getString("status"))
            );
        };
    }

    @Override
    public void insert(Incident incident) {
        String sql="INSERT INTO incident(type, details, status) " +
                " values (?,?,?)";
        jdbcTemplate.update(sql, incident.getIncidentType().toString(),
                                  incident.getProgressDetails(),
                                   incident.getIncidentStatus().toString());
        System.out.println("Incident added");


    }

    @Override
    public List<Incident> getAll() {
        //return List.of();
        String sql = "select * from incident";
        return jdbcTemplate.query(sql,mapper());

    }

    @Override
    public Incident getById(int id) throws ResourceNotFoundException {

        return null;
    }
    @Override
    public void deleteById(int id) throws ResourceNotFoundException {

    }

    @Override
    public void update(Incident incident)  {
        String sql = "UPDATE incident set details = ? WHERE id = ?";
        jdbcTemplate.update(sql, incident.getProgressDetails(), incident.getId());
        System.out.println("Record updated");

    }
}
