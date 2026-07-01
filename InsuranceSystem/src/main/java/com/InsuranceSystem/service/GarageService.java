package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.GarageRequestDto;
import com.InsuranceSystem.dto.GarageResponseDto;
import com.InsuranceSystem.dto.GarageStatusUpdateDto;
import com.InsuranceSystem.enums.GarageStatus;
import com.InsuranceSystem.mapper.GarageMapper;
import com.InsuranceSystem.model.Garage;
import com.InsuranceSystem.repository.GarageRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GarageService {

    private static final Logger log = LoggerFactory.getLogger(GarageService.class);

    private final GarageRepository garageRepository;

    public GarageResponseDto addGarage(GarageRequestDto request) {
        log.info("Adding new garage: {}", request.garageName());

        Garage garage = GarageMapper.toEntity(request);
        Garage savedGarage = garageRepository.save(garage);

        log.info("Garage added successfully with ID: {}", savedGarage.getGarageId());
        return GarageMapper.toDto(savedGarage);
    }

    public Page<GarageResponseDto> getAllGarages(int page, int size) {
        log.info("Fetching all garages - page: {}, size: {}", page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<Garage> garages = garageRepository.findAll(pageable);

        log.info("Found {} garage(s) on page {}", garages.getNumberOfElements(), page);
        return garages.map(GarageMapper::toDto);
    }

    public GarageResponseDto updateGarageStatus(int garageId, GarageStatusUpdateDto request) {
        log.info("Updating status for garage ID: {} to {}", garageId, request.status());

        Garage garage = garageRepository.findById(garageId)
                .orElseThrow(() -> {
                    log.error("Garage not found with ID: {}", garageId);
                    return new RuntimeException("Garage not found with ID: " + garageId);
                });

        garage.setStatus(request.status());
        Garage updatedGarage = garageRepository.save(garage);

        log.info("Garage ID: {} status updated to {}", garageId, request.status());
        return GarageMapper.toDto(updatedGarage);
    }

    public List<GarageResponseDto> getActiveGarages() {
        log.info("Fetching all active garages");

        List<Garage> garages = garageRepository.findByStatus(GarageStatus.ACTIVE);

        log.info("Found {} active garage(s)", garages.size());
        return garages.stream()
                .map(GarageMapper::toDto)
                .toList();
    }
}