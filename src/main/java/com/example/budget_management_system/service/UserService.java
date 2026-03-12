package com.example.budget_management_system.service;

import com.example.budget_management_system.entity.Location;
import com.example.budget_management_system.entity.User;
import com.example.budget_management_system.repository.LocationRepository;
import com.example.budget_management_system.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    public UserService(UserRepository userRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    public User createUser(String username, String email, String fullName, Long locationId) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + locationId));
        
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setLocation(location);
        
        return userRepository.save(user);
    }

    public Page<User> getAllUsers(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return userRepository.findAll(pageable);
    }

    public List<User> getUsersByLocationName(String locationName) {
        return userRepository.findAllByLocationName(locationName);
    }
    
    public List<User> getUsersByLocationCode(String locationCode) {
        return userRepository.findAllByLocationCode(locationCode);
    }

    public List<User> getUsersByProvinceCode(String provinceCode) {
        return userRepository.findAllByProvinceCode(provinceCode);
    }

    public List<User> getUsersByProvinceName(String provinceName) {
        return userRepository.findAllByProvinceName(provinceName);
    }

    public Page<User> getUsersByLocationNamePaginated(String locationName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        return userRepository.findAllByLocationName(locationName, pageable);
    }

    public Page<User> getUsersByProvinceCodePaginated(String provinceCode, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        return userRepository.findAllByProvinceCode(provinceCode, pageable);
    }
}
