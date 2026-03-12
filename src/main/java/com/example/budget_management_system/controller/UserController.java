package com.example.budget_management_system.controller;

import com.example.budget_management_system.entity.User;
import com.example.budget_management_system.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "username") String sortBy) {
        return ResponseEntity.ok(userService.getAllUsers(page, size, sortBy));
    }

    @GetMapping("/location/name/{locationName}")
    public ResponseEntity<List<User>> getUsersByLocationName(@PathVariable String locationName) {
        return ResponseEntity.ok(userService.getUsersByLocationName(locationName));
    }
    
    @GetMapping("/location/code/{locationCode}")
    public ResponseEntity<List<User>> getUsersByLocationCode(@PathVariable String locationCode) {
        return ResponseEntity.ok(userService.getUsersByLocationCode(locationCode));
    }

    @GetMapping("/province/code/{provinceCode}")
    public ResponseEntity<List<User>> getUsersByProvinceCode(@PathVariable String provinceCode) {
        return ResponseEntity.ok(userService.getUsersByProvinceCode(provinceCode));
    }

    @GetMapping("/province/name/{provinceName}")
    public ResponseEntity<List<User>> getUsersByProvinceName(@PathVariable String provinceName) {
        return ResponseEntity.ok(userService.getUsersByProvinceName(provinceName));
    }

    @GetMapping("/location/name/{locationName}/paginated")
    public ResponseEntity<Page<User>> getUsersByLocationNamePaginated(
            @PathVariable String locationName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getUsersByLocationNamePaginated(locationName, page, size));
    }

    @GetMapping("/province/code/{provinceCode}/paginated")
    public ResponseEntity<Page<User>> getUsersByProvinceCodePaginated(
            @PathVariable String provinceCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getUsersByProvinceCodePaginated(provinceCode, page, size));
    }
}
