package com.cms.repository;

import com.cms.model.Incident;
import com.cms.model.IncidentSuspect;
import com.cms.model.Suspect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentSuspectRepository extends JpaRepository<IncidentSuspect, Integer> {

}
