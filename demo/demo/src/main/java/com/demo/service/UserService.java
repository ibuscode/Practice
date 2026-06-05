package com.demo.service;

import com.demo.dto.EmployerRegisterRespDto;
import com.demo.dto.SeekerRegisterRespDto;
import com.demo.enums.Role;
import com.demo.model.Employer;
import com.demo.model.JobSeeker;
import com.demo.model.User;
import com.demo.repository.EmployeeRepository;
import com.demo.repository.JobSeekerRepository;
import com.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=  userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Invalid credentials"));
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void registerSeeker(SeekerRegisterRespDto dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.SEEKER);
        User savedUser = userRepository.save(user);
        JobSeeker seeker = new JobSeeker();
        seeker.setName(dto.name());
        seeker.setResumeSummary(dto.resumeSummary());
        seeker.setUser(savedUser);
        jobSeekerRepository.save(seeker);
    }

    public void registerEmployer(EmployerRegisterRespDto dto) {

        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.EMPLOYER);
        User savedUser = userRepository.save(user);
        Employer employer = new Employer();
        employer.setCompanyName(dto.companyName());
        employer.setUser(savedUser);
        employerRepository.save(employer);
    }
}
