package com.example.budget_management_system.repository;

import com.example.budget_management_system.entity.Location;
import com.example.budget_management_system.entity.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByCode(String code);
    Optional<Location> findByName(String name);
    List<Location> findByLocationType(LocationType locationType);
    Optional<Location> findByNameAndLocationType(String name, LocationType locationType);
}
