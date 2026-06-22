package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.VehicleRequestDto;
import com.InsuranceSystem.dto.VehicleResponseDto;
import com.InsuranceSystem.enums.VehicleType;
import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.model.Vehicle;
import com.InsuranceSystem.repository.CustomerRepository;
import com.InsuranceSystem.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private CustomerRepository customerRepository;  // ← mock this too

    @InjectMocks
    private VehicleService vehicleService;

    private Vehicle vehicle;
    private Customer customer;
    private String username;  // ← declare here

    @BeforeEach
    public void sampleData() {
        username = "testuser";  // ← define it here

        customer = new Customer();
        customer.setCustomerId(1);
        customer.setName("Test User");  // ← add this (mapper calls getName())


        vehicle = new Vehicle();
        vehicle.setVehicleId(1);
        vehicle.setVehicleType(VehicleType.CAMPERVAN);
        vehicle.setManufacturer("Hyundai");
        vehicle.setModel("i20");
        vehicle.setYear(2025);
        vehicle.setRegNo("TN36WE1234");
        vehicle.setCustomer(customer);  // ← add this, was missing

    }

    @Test
    void addVehicle_test() {
        // Arrange
        when(customerRepository.findByUserUsername(username))
                .thenReturn(Optional.of(customer));

        when(vehicleRepository.save(any(Vehicle.class)))
                .thenReturn(vehicle);

        VehicleRequestDto dto = new VehicleRequestDto(
                VehicleType.CAMPERVAN, "Hyundai", "i20", 2025, "TN36WE1234"
        );

        // Act
        Vehicle actualVehicle = vehicleService.addVehicle(dto, username);

        // Assert
        assertThat(actualVehicle.getVehicleType()).isEqualTo(vehicle.getVehicleType());
        assertThat(actualVehicle.getManufacturer()).isEqualTo(vehicle.getManufacturer());
        assertThat(actualVehicle.getModel()).isEqualTo(vehicle.getModel());

        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    void getMyVehicles_ReturnsEmptyList() {
        // Arrange
        when(customerRepository.findByUserUsername(username))
                .thenReturn(Optional.of(customer));

        when(vehicleRepository.findByCustomerCustomerId(customer.getCustomerId()))
                .thenReturn(List.of());

        // Act
        List<VehicleResponseDto> actualList = vehicleService.getMyVehicles(username);

        // Assert
        assertThat(actualList).hasSize(0);
        assertThat(actualList).isEmpty();
    }

    @Test
    void getMyVehicles_ReturnsVehicleList() {
        // Arrange
        when(customerRepository.findByUserUsername(username))
                .thenReturn(Optional.of(customer));

        when(vehicleRepository.findByCustomerCustomerId(customer.getCustomerId()))
                .thenReturn(List.of(vehicle));

        // Act
        List<VehicleResponseDto> actualList = vehicleService.getMyVehicles(username);

        // Assert
        assertThat(actualList).hasSize(1);
        assertThat(actualList.get(0).manufacturer()).isEqualTo(vehicle.getManufacturer());
        assertThat(actualList.get(0).model()).isEqualTo(vehicle.getModel());
        assertThat(actualList.get(0).vehicleType()).isEqualTo(vehicle.getVehicleType());
    }
}