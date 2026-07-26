package com.sivapavan.restoops.service;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sivapavan.restoops.dto.request.LoginRequest;
import com.sivapavan.restoops.dto.request.RegisterRequest;
import com.sivapavan.restoops.dto.response.AuthResponse;
import com.sivapavan.restoops.entity.Restaurant;
import com.sivapavan.restoops.entity.Role;
import com.sivapavan.restoops.entity.RoleName;
import com.sivapavan.restoops.entity.User;
import com.sivapavan.restoops.repository.RestaurantRepository;
import com.sivapavan.restoops.repository.RoleRepository;
import com.sivapavan.restoops.repository.UserRepository;
import com.sivapavan.restoops.security.JwtTokenProvider;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public AuthResponse register(RegisterRequest request) {
        log.info("Registering user: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Email Already Registered");
        }

        // Create restaurant
        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getRestaurantName());
        restaurant.setSlug(generateSlug(request.getRestaurantName()));
        restaurant = restaurantRepository.save(restaurant);

        // Get OWNER role
        Role ownerRole = roleRepository.findByName(RoleName.OWNER)
                .orElseThrow(() -> new IllegalStateException("OWNER role not found"));

        // Create user
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRestaurant(restaurant);
        user.setEnabled(true);
        user.setRoles(Set.of(ownerRole));

        userRepository.save(user);

        // ✅ Return using manual constructor
        return new AuthResponse(
                jwtTokenProvider.generateAccessToken(user.getEmail()),
                jwtTokenProvider.generateRefreshToken(user.getEmail()),
                "Bearer",
                user.getEmail(),
                user.getFullName()
        );
    }

    public AuthResponse login(LoginRequest request) {
        log.info("Login request for: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalStateException("Invalid email or password");
        }

        if (!user.isEnabled()) {
            throw new IllegalStateException("User account is disabled");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        // ✅ Return using manual constructor
        return new AuthResponse(
                accessToken,
                refreshToken,
                "Bearer",
                user.getEmail(),
                user.getFullName()
        );
    }

    private String generateSlug(String name) {
        return name.toLowerCase().trim().replaceAll("[^a-z0-9]+", "-");
    }
}