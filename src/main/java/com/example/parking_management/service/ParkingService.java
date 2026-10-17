package com.example.parking_management.service;

import com.example.parking_management.dto.TicketRequest;
import com.example.parking_management.dto.TicketResponse;
import com.example.parking_management.model.ParkingTicket;
import com.example.parking_management.model.Vehicle;
import com.example.parking_management.model.Zone;
import com.example.parking_management.repository.ParkingTicketRepository;
import com.example.parking_management.repository.VehicleRepository;
import com.example.parking_management.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ParkingService {

    @Autowired private VehicleRepository vehicleRepository;
    @Autowired private ZoneRepository zoneRepository;
    @Autowired private ParkingTicketRepository ticketRepository;

    @Transactional // Đảm bảo tính toàn vẹn dữ liệu
    public TicketResponse checkIn(TicketRequest req) {
        // 1. Kiểm tra Vehicle và Zone
        Vehicle vehicle = vehicleRepository.findById(req.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        Zone zone = zoneRepository.findById(req.getZoneId())
                .orElseThrow(() -> new RuntimeException("Zone not found"));

        // 2. Kiểm tra xem Zone còn chỗ không
        if (zone.getOccupiedSpots() >= zone.getCapacity()) {
            throw new RuntimeException("Zone is full");
        }

        // 3. Tạo ParkingTicket
        ParkingTicket ticket = new ParkingTicket();
        ticket.setVehicle(vehicle);
        ticket.setZone(zone);
        ticket.setCheckInTime(LocalDateTime.now());

        // 4. Cập nhật số chỗ đã dùng của Zone
        zone.setOccupiedSpots(zone.getOccupiedSpots() + 1);
        zoneRepository.save(zone);

        // 5. Lưu vé
        ParkingTicket savedTicket = ticketRepository.save(ticket);

        return new TicketResponse(savedTicket.getId(), vehicle.getLicensePlate(), vehicle.getType(),
                zone.getName(), savedTicket.getCheckInTime(), savedTicket.getCheckOutTime());
    }

    @Transactional // Đảm bảo tính toàn vẹn dữ liệu
    public TicketResponse checkOut(Long vehicleId) {
        // 1. Tìm vé chưa check-out
        ParkingTicket ticket = ticketRepository.findActiveTicketByVehicleId(vehicleId)
                .orElseThrow(() -> new RuntimeException("Active ticket not found for this vehicle"));

        // 2. Cập nhật thời gian ra
        ticket.setCheckOutTime(LocalDateTime.now());

        // 3. Giảm số chỗ đã dùng của Zone
        Zone zone = ticket.getZone();
        zone.setOccupiedSpots(zone.getOccupiedSpots() - 1);
        zoneRepository.save(zone);

        // 4. Lưu vé
        ParkingTicket updatedTicket = ticketRepository.save(ticket);

        return new TicketResponse(updatedTicket.getId(), ticket.getVehicle().getLicensePlate(),
                ticket.getVehicle().getType(), zone.getName(),
                updatedTicket.getCheckInTime(), updatedTicket.getCheckOutTime());
    }
}