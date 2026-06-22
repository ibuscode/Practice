package com.InsuranceSystem.mapper;

import com.InsuranceSystem.dto.GarageRequestDto;
import com.InsuranceSystem.dto.GarageResponseDto;
import com.InsuranceSystem.enums.GarageStatus;
import com.InsuranceSystem.model.Garage;

public class GarageMapper {

    public static Garage toEntity(GarageRequestDto request) {
        Garage garage = new Garage();
        garage.setGarageName(request.garageName());
        garage.setAddress(request.address());
        garage.setCity(request.city());
        garage.setContactNumber(request.contactNumber());
        garage.setStatus(GarageStatus.ACTIVE);  // Default status
        return garage;
    }

    public static GarageResponseDto toDto(Garage garage) {
        return new GarageResponseDto(
                garage.getGarageId(),
                garage.getGarageName(),
                garage.getAddress(),
                garage.getCity(),
                garage.getContactNumber(),
                garage.getStatus()
        );
    }
}
