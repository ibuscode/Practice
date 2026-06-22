package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.GarageRequestDto;
import com.InsuranceSystem.dto.GarageResponseDto;
import com.InsuranceSystem.dto.GarageStatusUpdateDto;
import com.InsuranceSystem.enums.GarageStatus;
import com.InsuranceSystem.mapper.GarageMapper;
import com.InsuranceSystem.model.Garage;
import com.InsuranceSystem.repository.GarageRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GarageService {

    private final GarageRepository garageRepository;
    public GarageResponseDto addGarage(GarageRequestDto request) {
        Garage garage = GarageMapper.toEntity(request);
        Garage savedGarage = garageRepository.save(garage);
        return GarageMapper.toDto(savedGarage);
    }

    public Page<GarageResponseDto> getAllGarages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Garage> garages = garageRepository.findAll(pageable);

        return garages.map(GarageMapper::toDto);
    }

    public GarageResponseDto updateGarageStatus(int garageId, GarageStatusUpdateDto request) {
        Garage garage = garageRepository.findById(garageId)
                .orElseThrow(() -> new RuntimeException("Garage not found with ID: " + garageId));

        garage.setStatus(request.status());
        Garage updatedGarage = garageRepository.save(garage);
        return GarageMapper.toDto(updatedGarage);
    }
    public List<GarageResponseDto> getActiveGarages() {
        List<Garage> garages = garageRepository.findByStatus(GarageStatus.ACTIVE);

        return garages.stream()
                .map(GarageMapper::toDto)
                .toList();
    }

}
