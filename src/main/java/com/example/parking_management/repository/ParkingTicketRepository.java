package com.example.parking_management.repository;

import com.example.parking_management.dto.TicketSummaryResponse;
import com.example.parking_management.model.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {

    // Tìm vé gần nhất của xe mà chưa có thời gian ra (checkOutTime is null) - Dùng cho Check-out
    @Query("SELECT t FROM ParkingTicket t WHERE t.vehicle.id = :vehicleId AND t.checkOutTime IS NULL")
    Optional<ParkingTicket> findActiveTicketByVehicleId(@Param("vehicleId") Long vehicleId);

    // Sử dụng DTO Projection với 'SELECT new...' để tối ưu hiệu năng - Dùng cho Summary
    @Query("""
           SELECT new com.example.parking_management.dto.TicketSummaryResponse(
               t.id, 
               t.vehicle.licensePlate, 
               t.zone.name, 
               t.checkInTime, 
               t.checkOutTime
           )
           FROM ParkingTicket t
           WHERE t.checkInTime >= :startOfDay AND t.checkInTime <= :endOfDay
           """)
    List<TicketSummaryResponse> getTodayTicketSummary(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);
}