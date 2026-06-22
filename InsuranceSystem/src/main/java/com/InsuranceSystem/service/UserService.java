package com.InsuranceSystem.service;

import com.InsuranceSystem.dto.CustomerRegisterDto;
import com.InsuranceSystem.dto.OfficerDto;
import com.InsuranceSystem.dto.UserProfileDto;
import com.InsuranceSystem.enums.Role;
import com.InsuranceSystem.exceptions.ResourceNotFoundException;
import com.InsuranceSystem.mapper.UserMapper;
import com.InsuranceSystem.model.Customer;
import com.InsuranceSystem.model.Officer;
import com.InsuranceSystem.model.User;
import com.InsuranceSystem.repository.CustomerRepository;
import com.InsuranceSystem.repository.OfficerRepository;
import com.InsuranceSystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final OfficerRepository officerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Invalid Credentials"));
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getUser(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid User Id"));
    }

    public void registerCustomer(CustomerRegisterDto dto) {
        User user = UserMapper.customerDtoToUser(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        userRepository.save(user);

        Customer customer = UserMapper.customerDtoToCustomer(dto);
        customer.setUser(user);
         customerRepository.save(customer);
    }



   /* public UserProfileDto getProfile(int id, String loginUsername) {
        User loginUser = (User) loadUserByUsername(loginUsername);
        User user = getUser(id);

        if (loginUser.getRole() == Role.CUSTOMER && loginUser.getId() != id) {
            throw new RuntimeException("You can see only your profile");
        }

        if (user.getRole() == Role.CUSTOMER) {
            Customer customer = customerRepository.findByUserId(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer profile not found"));
            return UserMapper.customerToProfileDto(customer);
        }

        Officer officer = officerRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Officer profile not found"));
        return UserMapper.officerToProfileDto(officer);
    }

    public UserProfileDto updateProfile(int id, UserProfileDto dto, String loginUsername) {
        User loginUser = (User) loadUserByUsername(loginUsername);

        if (loginUser.getId() != id) {
            throw new RuntimeException("You can update only your profile");
        }

        User user = getUser(id);
        if (dto.username() != null) {
            user.setUsername(dto.username());
        }
        userRepository.save(user);

        if (user.getRole() == Role.CUSTOMER) {
            Customer customer = customerRepository.findByUserId(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer profile not found"));
            customer.setName(dto.name());
            customer.setEmail(dto.email());
            customer.setAddress(dto.address());
            customer.setDateOfBirth(dto.dateOfBirth());
            customer.setPhoneNumber(dto.phoneNumber());
            customerRepository.save(customer);
            return UserMapper.customerToProfileDto(customer);
        }

        Officer officer = officerRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Officer profile not found"));
        officer.setName(dto.name());
        officer.setEmail(dto.email());
        officerRepository.save(officer);
        return UserMapper.officerToProfileDto(officer);
    }

    public void deleteUser(int id, String loginUsername) {
        User loginUser = (User) loadUserByUsername(loginUsername);

        if (loginUser.getRole() != Role.ADMIN && loginUser.getId() != id) {
            throw new RuntimeException("You can delete only your account");
        }

        User user = getUser(id);
        if (user.getRole() == Role.OFFICER && loginUser.getRole() != Role.ADMIN) {
            throw new RuntimeException("Officer cannot delete account");
        }

        if (user.getRole() == Role.CUSTOMER) {
            customerRepository.findByUserId(id).ifPresent(customerRepository::delete);
        }

        if (user.getRole() == Role.OFFICER) {
            officerRepository.findByUserId(id).ifPresent(officerRepository::delete);
        }

        userRepository.delete(user);
    }*/
}
