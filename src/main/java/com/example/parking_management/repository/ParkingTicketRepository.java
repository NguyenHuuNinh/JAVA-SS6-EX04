package com.example.parking_management.repository;

import com.example.parking_management.model.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {

    // Tìm vé gần nhất của xe mà chưa có thời gian ra (checkOutTime is null)
    @Query("SELECT t FROM ParkingTicket t WHERE t.vehicle.id = :vehicleId AND t.checkOutTime IS NULL")
    Optional<ParkingTicket> findActiveTicketByVehicleId(@Param("vehicleId") Long vehicleId);
}