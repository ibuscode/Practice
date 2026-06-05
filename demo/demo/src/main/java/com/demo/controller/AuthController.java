package com.demo.controller;

import com.demo.dto.EmployerRegisterRespDto;
import com.demo.dto.SeekerRegisterRespDto;
import com.demo.utility.JwtUtility;
import com.demo.dto.LoginResponseDto;
import com.demo.dto.TokenDto;
import com.demo.model.User;
import com.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtility jwtUtility;
    @GetMapping("/login")
    public TokenDto login(Principal principal){
        String username = principal.getName();
        String token = jwtUtility.generateToken(username);
        return new TokenDto(username,token);
    }

    // this is for later
    @GetMapping("/user-details")
    public LoginResponseDto getUserDetails(Principal principal){
        User user = (User)userService.loadUserByUsername(principal.getName());
        return new LoginResponseDto(
                user.getId(),
                user.getUsername(),
                user.getRole().toString()
        );
    }

    @PostMapping("/register/seeker")
    public void register(@Valid @RequestBody SeekerRegisterRespDto dto) {
        userService.registerSeeker(dto);
    }
    @PostMapping("/register/employer")
    public String registerEmployer(@Valid @RequestBody EmployerRegisterRespDto dto) {  // ← Correct DTO
        userService.registerEmployer(dto);
        return "Employer registered successfully";
    }
}
