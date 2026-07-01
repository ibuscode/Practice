package com.InsuranceSystem.controller;

import com.InsuranceSystem.dto.UserProfileDto;
import com.InsuranceSystem.service.UserService;
import com.InsuranceSystem.utility.ResponseUtility;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

  /*  @GetMapping("/api/v1/users/{id}")
    public UserProfileDto getProfile(@PathVariable int id, Principal principal) {
        return userService.getProfile(id, principal.getName());
    }

    @PutMapping("/api/v1/users/{id}")
    public UserProfileDto updateProfile(@PathVariable int id, @RequestBody UserProfileDto dto, Principal principal) {
        return userService.updateProfile(id, dto, principal.getName());
    }

    @DeleteMapping("/api/v1/users/{id}")
    public ResponseUtility deleteUser(@PathVariable int id, Principal principal) {
        userService.deleteUser(id, principal.getName());
        return new ResponseUtility("User deleted successfully");
    }*/
}
