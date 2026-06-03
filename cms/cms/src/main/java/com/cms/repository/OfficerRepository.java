package com.cms.repository;

import com.cms.dto.IncidentOfficerStatJpqlDto;
import com.cms.model.Officer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficerRepository extends JpaRepository<Officer, Integer> {
    @Query("""
            select o from Officer o where o.station.stationHead.user.username =?1
            """)
     List<Officer> getOfficerByStationHead(String stationHeadUsername);

    @Query("""
         select o from Officer o join Incident i ON o.id = i.officer.id where i.id =?1
""")
     Officer getByIncidentId(int incidentId);


@Query(
        """
     select i.officer.name as name, count(i.id) as numberOfIncidents from Incident i
     group by i.officer.id
"""
)
    List<IncidentOfficerStatJpqlDto> incidentByOfficerStat();
}
