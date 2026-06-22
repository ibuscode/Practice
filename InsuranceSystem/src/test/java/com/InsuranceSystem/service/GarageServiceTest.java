package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.GarageRequestDto;
import com.InsuranceSystem.dto.GarageResponseDto;
import com.InsuranceSystem.dto.GarageStatusUpdateDto;
import com.InsuranceSystem.enums.GarageStatus;
import com.InsuranceSystem.model.Garage;
import com.InsuranceSystem.repository.GarageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GarageServiceTest {

    @Mock
    private GarageRepository garageRepository;

    @InjectMocks
    private GarageService garageService;

    private Garage garage;
    private Garage garage1;
    private Garage garage2;

    @BeforeEach
    public void sampleData() {
        garage = new Garage();
        garage.setGarageId(1);
        garage.setGarageName("Chennai Auto Works");
        garage.setAddress("123 Anna Salai");
        garage.setCity("Chennai");
        garage.setContactNumber("9876543210");
        garage.setStatus(GarageStatus.ACTIVE);

        garage1 = new Garage();
        garage1.setGarageId(2);
        garage1.setGarageName("Velachery Motors");
        garage1.setAddress("45 Velachery Main Road");
        garage1.setCity("Chennai");
        garage1.setContactNumber("9123456780");
        garage1.setStatus(GarageStatus.ACTIVE);

        garage2 = new Garage();
        garage2.setGarageId(3);
        garage2.setGarageName("T Nagar Service Hub");
        garage2.setAddress("78 Pondy Bazaar");
        garage2.setCity("Chennai");
        garage2.setContactNumber("9988776655");
        garage2.setStatus(GarageStatus.INACTIVE);
    }

    // ─── addGarage ───────────────────────────────────────────────

    @Test
    void addGarage_mustSaveAndReturnGarage() {
        when(garageRepository.save(any(Garage.class))).thenReturn(garage);

        GarageRequestDto dto = new GarageRequestDto(
                "Chennai Auto Works",
                "123 Anna Salai",
                "Chennai",
                "9876543210"
        );

        GarageResponseDto actual = garageService.addGarage(dto);

        assertThat(actual.garageName()).isEqualTo(garage.getGarageName());
        assertThat(actual.city()).isEqualTo(garage.getCity());
        assertThat(actual.status()).isEqualTo(garage.getStatus());

        verify(garageRepository, times(1)).save(any(Garage.class));
    }

    // ─── getAllGarages ────────────────────────────────────────────

    @Test
    void getAllGarages_mustReturnPagedResult() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Garage> pagedGarages = new PageImpl<>(List.of(garage, garage1), pageable, 2);

        when(garageRepository.findAll(pageable)).thenReturn(pagedGarages);

        Page<GarageResponseDto> actual = garageService.getAllGarages(0, 10);

        assertThat(actual.getTotalElements()).isEqualTo(2);
        assertThat(actual.getContent().get(0).garageName()).isEqualTo("Chennai Auto Works");
        assertThat(actual.getContent().get(1).garageName()).isEqualTo("Velachery Motors");

        verify(garageRepository, times(1)).findAll(pageable);
    }

    @Test
    void getAllGarages_mustReturnEmptyPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Garage> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        when(garageRepository.findAll(pageable)).thenReturn(emptyPage);

        Page<GarageResponseDto> actual = garageService.getAllGarages(0, 10);

        assertThat(actual).isEmpty();
        assertThat(actual.getTotalElements()).isEqualTo(0);

        verify(garageRepository, times(1)).findAll(pageable);
    }

    // ─── updateGarageStatus ──────────────────────────────────────

    @Test
    void updateGarageStatus_mustUpdateAndReturn() {
        when(garageRepository.findById(1)).thenReturn(Optional.of(garage));
        when(garageRepository.save(any(Garage.class))).thenReturn(garage);

        GarageStatusUpdateDto dto = new GarageStatusUpdateDto(GarageStatus.INACTIVE);

        GarageResponseDto actual = garageService.updateGarageStatus(1, dto);

        assertThat(actual.garageName()).isEqualTo(garage.getGarageName());

        verify(garageRepository, times(1)).findById(1);
        verify(garageRepository, times(1)).save(any(Garage.class));
    }

    @Test
    void updateGarageStatus_garageNotFound_throwsException() {
        when(garageRepository.findById(999)).thenReturn(Optional.empty());

        GarageStatusUpdateDto dto = new GarageStatusUpdateDto(GarageStatus.INACTIVE);

        assertThatThrownBy(() -> garageService.updateGarageStatus(999, dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Garage not found with ID: 999");

        verify(garageRepository, times(1)).findById(999);
        verify(garageRepository, never()).save(any(Garage.class));
    }

    // ─── getActiveGarages ────────────────────────────────────────

    @Test
    void getActiveGarages_mustReturnOnlyActiveGarages() {
        when(garageRepository.findByStatus(GarageStatus.ACTIVE))
                .thenReturn(List.of(garage, garage1));

        List<GarageResponseDto> actual = garageService.getActiveGarages();

        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).garageName()).isEqualTo("Chennai Auto Works");
        assertThat(actual.get(1).garageName()).isEqualTo("Velachery Motors");
        assertThat(actual.get(0).status()).isEqualTo(GarageStatus.ACTIVE);
        assertThat(actual.get(1).status()).isEqualTo(GarageStatus.ACTIVE);

        verify(garageRepository, times(1)).findByStatus(GarageStatus.ACTIVE);
    }

    @Test
    void getActiveGarages_mustReturnEmptyList() {
        when(garageRepository.findByStatus(GarageStatus.ACTIVE)).thenReturn(List.of());

        List<GarageResponseDto> actual = garageService.getActiveGarages();

        assertThat(actual).isEmpty();
        assertThat(actual).hasSize(0);

        verify(garageRepository, times(1)).findByStatus(GarageStatus.ACTIVE);
    }
}