package com.InsuranceSystem.config;

import com.InsuranceSystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final UserService userService;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) /// Spring needs this for POST,PUT & DELETE
                //.csrf(ref->ref.disable())
                .authorizeHttpRequests(authorize -> authorize
                                  //Authentication
                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/customer/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/admin/officers").hasAuthority("ADMIN")
                                   //user management
                                .requestMatchers(HttpMethod.GET, "/api/v1/users/{id}").hasAnyAuthority("CUSTOMER", "OFFICER")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}").hasAnyAuthority("CUSTOMER", "OFFICER")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/{id}").hasAnyAuthority("CUSTOMER", "ADMIN")
                                  //vehicle
                                .requestMatchers(HttpMethod.POST, "/api/vehicle/add/{customerId}").hasAuthority("CUSTOMER")
                                .requestMatchers(HttpMethod.GET, "/api/vehicle/customer/getAll/{customerId}").hasAuthority("CUSTOMER")
                                  //policy types
                                .requestMatchers(HttpMethod.GET, "/api/policyType/getAll").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/policy-types/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/policy-types").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/policy-types/{id}").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/policy-types/{id}/addons").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/policy-types/{id}/addons").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/addons/{id}").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/addons/{id}").hasAuthority("OFFICER")
                                  //proposalsapi
                                .requestMatchers(HttpMethod.POST, "/api/v1/proposals").hasAuthority("CUSTOMER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/proposals/user/{userId}").hasAnyAuthority("CUSTOMER", "OFFICER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/proposals/{id}").hasAnyAuthority("CUSTOMER", "OFFICER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/proposals").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/proposals/{id}/approve").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/proposals/{id}/reject").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/proposals/{id}/request-info").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.POST, "/api/v1/proposals/{id}/documents").hasAuthority("CUSTOMER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/proposals/{id}/documents").hasAnyAuthority("CUSTOMER", "OFFICER")
                                  //quotesapi
                                .requestMatchers(HttpMethod.POST, "/api/v1/quotes").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/quotes/{proposalId}").hasAnyAuthority("CUSTOMER", "OFFICER")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/quotes/{id}/expire").hasAuthority("OFFICER")
                                  //policiesapi
                                .requestMatchers(HttpMethod.GET, "/api/policies/user/{userId}").hasAnyAuthority("CUSTOMER", "OFFICER")
                                .requestMatchers(HttpMethod.GET, "/api/policies/getBy-id/{id}").hasAnyAuthority("CUSTOMER", "OFFICER")
                                  //claimsapi
                                .requestMatchers(HttpMethod.POST, "/api/claims/create").hasAuthority("CUSTOMER")
                                .requestMatchers(HttpMethod.GET, "/api/claims/get-user/{userId}").hasAnyAuthority("CUSTOMER", "OFFICER")


                                .anyRequest().authenticated()

                        //.anyRequest().denyAll()
                        // .anyRequest().permitAll() -- all API work without Auth
                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults()); //i am telling Spring that i am using Basic Auth technique
        return http.build();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider(userService);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
