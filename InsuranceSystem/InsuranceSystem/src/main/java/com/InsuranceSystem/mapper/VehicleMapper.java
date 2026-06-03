package com.InsuranceSystem.mapper;

import com.InsuranceSystem.dto.VehicleResDto;
import com.InsuranceSystem.model.Vehicle;

public class VehicleMapper {

    public static Vehicle entityToDto(VehicleResDto dto)
    {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleType(dto.vehicleType());
        vehicle.setModel(dto.model());
        vehicle.setYear(dto.year());
        vehicle.setRegNo(dto.regNo());
        vehicle.setAgeOfVehicle(dto.ageOfVehicle());
        return vehicle;
    }
    public static VehicleResDto entityToDto(Vehicle vehicle)
    {
        return new VehicleResDto(
                vehicle.getVehicleType(),
                vehicle.getModel(),
                vehicle.getYear(),
                vehicle.getRegNo(),
                vehicle.getAgeOfVehicle()
        );
    }

}
