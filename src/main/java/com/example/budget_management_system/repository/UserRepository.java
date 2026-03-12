package com.example.budget_management_system.repository;

import com.example.budget_management_system.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.location.name = :locationName")
    List<User> findAllByLocationName(@Param("locationName") String locationName);
    
    @Query("SELECT u FROM User u WHERE u.location.code = :locationCode")
    List<User> findAllByLocationCode(@Param("locationCode") String locationCode);
    
    @Query("SELECT u FROM User u WHERE u.location.parent.name = :parentName")
    List<User> findAllByParentLocationName(@Param("parentName") String parentName);
    
    @Query("SELECT u FROM User u WHERE u.location.parent.parent.name = :grandParentName")
    List<User> findAllByGrandParentLocationName(@Param("grandParentName") String grandParentName);
    
    @Query("SELECT u FROM User u WHERE u.location.parent.parent.parent.name = :greatGrandParentName")
    List<User> findAllByGreatGrandParentLocationName(@Param("greatGrandParentName") String greatGrandParentName);
    
    @Query("SELECT u FROM User u WHERE u.location.parent.parent.parent.parent.name = :provinceName")
    List<User> findAllByProvinceName(@Param("provinceName") String provinceName);
    
    @Query("SELECT u FROM User u WHERE u.location.parent.parent.parent.parent.code = :provinceCode")
    List<User> findAllByProvinceCode(@Param("provinceCode") String provinceCode);
    
    @Query("SELECT u FROM User u WHERE u.location.name = :locationName")
    Page<User> findAllByLocationName(@Param("locationName") String locationName, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.location.parent.parent.parent.parent.code = :provinceCode")
    Page<User> findAllByProvinceCode(@Param("provinceCode") String provinceCode, Pageable pageable);
}
