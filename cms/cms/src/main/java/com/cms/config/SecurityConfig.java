package com.cms.config;

import com.cms.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

   /* @Bean
    public UserDetailsService users() {
        UserDetails user1 = User.builder()
                .username("john_doe")
                .password("{noop}password123")
                .roles("OFFICER")
                .build();
        UserDetails user2 = User.builder()
                .username("robert_davids")
                .password("{noop}pass123")
                .roles("STATION_HEAD")
                .build();
        return new InMemoryUserDetailsManager(user1,user2 );
    }*/
    private final UserService userService;
    private final JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) /// Spring needs this for POST,PUT & DELETE
                //.csrf(ref->ref.disable())
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(HttpMethod.POST, "/api/officer/add").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/station/by-incident/{incidentId}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/incident/get-one/{id}").hasAnyRole("OFFICER", "STATION_HEAD")
                                .requestMatchers(HttpMethod.GET, "/api/officer/by-incident/stat").permitAll()
                                .anyRequest().authenticated()
                        // .anyRequest().permitAll() -- all API work without Auth
                );
       // http.httpBasic(Customizer.withDefaults()); //I am telling Spring that I am using Basic Auth technique
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults()); //i am telling Spring that i am using Basic Auth technique
        return http.build();
    }
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider(userService);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    }

