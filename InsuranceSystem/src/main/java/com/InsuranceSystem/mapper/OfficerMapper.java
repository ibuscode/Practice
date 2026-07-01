package com.InsuranceSystem.mapper;

import com.InsuranceSystem.dto.OfficerProfileDto;
import com.InsuranceSystem.dto.OfficerResponseDto;
import com.InsuranceSystem.model.Officer;

public class OfficerMapper {
    public static OfficerResponseDto toDto(Officer officer) {
        return new OfficerResponseDto(
                officer.getOfficerId(),
                officer.getName()
        );
    }

    public static OfficerProfileDto toProfileDto(Officer officer) {
        return new OfficerProfileDto(
                officer.getOfficerId(),
                officer.getName(),
                officer.getUser().getRole().toString()
        );
    }
}
