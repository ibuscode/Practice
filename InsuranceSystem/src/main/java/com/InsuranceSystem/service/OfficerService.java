package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.OfficerDto;
import com.InsuranceSystem.dto.OfficerProfileDto;
import com.InsuranceSystem.dto.OfficerResponseDto;
import com.InsuranceSystem.enums.Role;
import com.InsuranceSystem.exceptions.UserAlreadyPresentException;
import com.InsuranceSystem.mapper.OfficerMapper;
import com.InsuranceSystem.model.Officer;
import com.InsuranceSystem.model.User;
import com.InsuranceSystem.repository.OfficerRepository;
import com.InsuranceSystem.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfficerService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final OfficerRepository officerRepository;
    private final UserRepository userRepository;

    @Value("${officer.password.temp}")
    private String officerTempPassword;

    public void createOfficer(@Valid OfficerDto dto) {
        String username = dto.username();
        String password = officerTempPassword;
        Role role = Role.OFFICER;

        //check for username uniqueness

        if(userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyPresentException(
                    "Username is already taken"
            );
        }



        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRole(role);

        user = userService.save(user);
        Officer officer= new Officer();
        officer.setName(dto.name());
        officer.setUser(user);

        officerRepository.save(officer);

    }

    // Get all officers
    public List<OfficerResponseDto> getAllOfficers() {
        List<Officer> officers = officerRepository.findAll();

        return officers.stream()
                .map(OfficerMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get officer by ID
    public OfficerResponseDto getOfficerById(int officerId) {
        Officer officer = officerRepository.findById(officerId)
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        return OfficerMapper.toDto(officer);
    }

    public OfficerProfileDto getOfficerProfile(String username) {
        Officer officer = officerRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        return OfficerMapper.toProfileDto(officer);
    }
}
