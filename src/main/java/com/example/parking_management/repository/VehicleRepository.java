package com.example.parking_management.repository;

import com.example.parking_management.dto.VehicleResponse;
import com.example.parking_management.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("""
           SELECT new com.example.parking_management.dto.VehicleResponse(
               v.id, v.licensePlate, v.color, v.type
           )
           FROM Vehicle v
           WHERE (:keyword IS NULL OR v.licensePlate LIKE %:keyword%)
           """)
    Page<VehicleResponse> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}