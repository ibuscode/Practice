package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.VehicleResDto;
import com.InsuranceSystem.mapper.VehicleMapper;
import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.model.Vehicle;
import com.InsuranceSystem.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VehicleService {

    private final CustomerService customerService;
    private final VehicleRepository vehicleRepository;



    public void add(int customerId, VehicleResDto dto) {
        Customer customer  = customerService.getById(customerId);
         Vehicle vehicle = VehicleMapper.entityToDto(dto);
        vehicle.setCustomer(customer);
         vehicleRepository.save(vehicle);

    }

    public List<VehicleResDto> getById(int customerId) {

        List<Vehicle> list = vehicleRepository.getById(customerId);

        if (list.isEmpty()) {
            throw new RuntimeException("No vehicles found for customer " + customerId);
        }
        return list.stream()
                .map(VehicleMapper::entityToDto)
                .toList();


    }
}
