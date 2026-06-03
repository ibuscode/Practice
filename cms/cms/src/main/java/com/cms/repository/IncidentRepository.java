package com.cms.repository;

import com.cms.dto.IncidentStatDto;
import com.cms.enums.IncidentStatus;
import com.cms.enums.IncidentType;
import com.cms.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Integer> {
    List<Incident> findByIncidentType(IncidentType type);
    List<Incident> findByIncidentStatus(IncidentStatus incidentStatus);

    List<Incident> findByOfficerId(int officerId);

    @Query(
            """
      select i.incidentType as incidentType, count(i.id) from Incident i
      group by i.incidentType
"""
    )
    List<IncidentStatDto> incidentStat();
}
