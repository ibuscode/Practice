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
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                //.requestMatchers(HttpMethod.GET, "/api/uploads/receipts/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/uploads/**").permitAll()


                                .requestMatchers(HttpMethod.POST, "/api/auth/customer/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/admin/officers").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/auth/user-details").authenticated()

                                .requestMatchers(HttpMethod.GET, "/api/customer/profile").hasAuthority("CUSTOMER")
                                .requestMatchers(HttpMethod.PUT, "/api/customer/profile/edit").hasAuthority("CUSTOMER")

                                .requestMatchers(HttpMethod.GET, "/api/officer/profile").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.GET, "/api/officer/stats").hasAuthority("OFFICER")

                                //vehicle
                                .requestMatchers(HttpMethod.POST, "/api/vehicles/add").hasAuthority("CUSTOMER")
                                .requestMatchers(HttpMethod.GET, "/api/vehicles/my-vehicles").hasAuthority("CUSTOMER")
                                .requestMatchers(HttpMethod.GET, "/api/vehicles/my-vehicles/policies").hasAuthority("CUSTOMER")

                                //quotes
                                .requestMatchers(HttpMethod.POST, "/api/quotes/generate").hasAuthority("CUSTOMER")
                                .requestMatchers(HttpMethod.GET, "/api/customer/stat/by-type").hasAuthority("CUSTOMER")

                                //upload doc
                                .requestMatchers(HttpMethod.POST, "/api/proposal/rcbook/upload").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/proposal/license/upload").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/proposal/vehiclephoto/upload").hasAuthority("CUSTOMER")
                                //assign officer

                                .requestMatchers(HttpMethod.POST, "/api/proposal/assign-officer").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/proposal/submitted").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/proposal/officers/get").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/proposal/my-assigned-proposals").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.GET, "/api/proposal/details/{proposalId}").hasAnyAuthority("OFFICER","CUSTOMER")
                                .requestMatchers(HttpMethod.PUT, "/api/proposal/approve/{proposalId}").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.PUT, "/api/proposal/reject/{proposalId}").hasAuthority("OFFICER")
                                 //my proposals
                                .requestMatchers(HttpMethod.GET, "/api/customer/my-proposals").hasAuthority("CUSTOMER")
                                .requestMatchers(HttpMethod.POST, "/api/payments/pay").hasAuthority("CUSTOMER")

                                .requestMatchers(HttpMethod.GET, "/api/policies/my-policies").hasAuthority("CUSTOMER")

                                .requestMatchers(HttpMethod.POST, "/api/claims/file").hasAuthority("CUSTOMER")
                                .requestMatchers(HttpMethod.GET, "/api/claims/my-claims").hasAuthority("CUSTOMER")
                                .requestMatchers(HttpMethod.GET, "/api/claims/all").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.GET, "/api/claims/assigned").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.GET, "/api/claims/assigned/submitted").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.GET, "/api/claims/details/{claimId}").hasAnyAuthority("OFFICER","CUSTOMER")
                                .requestMatchers(HttpMethod.PUT, "/api/claims/{claimId}/review").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.PUT, "/api/claims/{claimId}/approve").hasAuthority("OFFICER")
                                .requestMatchers(HttpMethod.PUT, "/api/claims/{claimId}/reject").hasAuthority("OFFICER")

// Garage endpoints
                                .requestMatchers(HttpMethod.POST, "/api/garages/add").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/garages/all").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/{garageId}/status").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/garages/active").hasAuthority("CUSTOMER")

                                // Receipt upload and view endpoints
                                .requestMatchers(HttpMethod.POST, "/api/claims/receipt/upload").hasAuthority("CUSTOMER")

                                .requestMatchers(HttpMethod.GET, "/api/public/stats").permitAll()









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
