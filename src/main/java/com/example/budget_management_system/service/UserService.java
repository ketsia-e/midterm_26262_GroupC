package com.example.budget_management_system.service;

import com.example.budget_management_system.entity.User;
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

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
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
