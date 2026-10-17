package com.example.parking_management.service;

import com.example.parking_management.dto.TicketRequest;
import com.example.parking_management.dto.TicketResponse;
import com.example.parking_management.dto.TicketSummaryResponse;
import com.example.parking_management.model.ParkingTicket;
import com.example.parking_management.model.Vehicle;
import com.example.parking_management.model.Zone;
import com.example.parking_management.repository.ParkingTicketRepository;
import com.example.parking_management.repository.VehicleRepository;
import com.example.parking_management.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ParkingService {

    @Autowired private VehicleRepository vehicleRepository;
    @Autowired private ZoneRepository zoneRepository;
    @Autowired private ParkingTicketRepository ticketRepository;

    @Transactional
    public TicketResponse checkIn(TicketRequest req) {
        Vehicle vehicle = vehicleRepository.findById(req.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        Zone zone = zoneRepository.findById(req.getZoneId())
                .orElseThrow(() -> new RuntimeException("Zone not found"));

        if (zone.getOccupiedSpots() >= zone.getCapacity()) {
            throw new RuntimeException("Zone is full");
        }

        ParkingTicket ticket = new ParkingTicket();
        ticket.setVehicle(vehicle);
        ticket.setZone(zone);
        ticket.setCheckInTime(LocalDateTime.now());

        zone.setOccupiedSpots(zone.getOccupiedSpots() + 1);
        zoneRepository.save(zone);

        ParkingTicket savedTicket = ticketRepository.save(ticket);

        return new TicketResponse(savedTicket.getId(), vehicle.getLicensePlate(), vehicle.getType(),
                zone.getName(), savedTicket.getCheckInTime(), savedTicket.getCheckOutTime());
    }

    @Transactional
    public TicketResponse checkOut(Long vehicleId) {
        ParkingTicket ticket = ticketRepository.findActiveTicketByVehicleId(vehicleId)
                .orElseThrow(() -> new RuntimeException("Active ticket not found for this vehicle"));

        ticket.setCheckOutTime(LocalDateTime.now());

        Zone zone = ticket.getZone();
        zone.setOccupiedSpots(zone.getOccupiedSpots() - 1);
        zoneRepository.save(zone);

        ParkingTicket updatedTicket = ticketRepository.save(ticket);

        return new TicketResponse(updatedTicket.getId(), ticket.getVehicle().getLicensePlate(),
                ticket.getVehicle().getType(), zone.getName(),
                updatedTicket.getCheckInTime(), updatedTicket.getCheckOutTime());
    }

    // Lấy danh sách tóm tắt các vé gửi xe trong ngày hôm nay
    public List<TicketSummaryResponse> getTodayTicketSummary() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        return ticketRepository.getTodayTicketSummary(startOfDay, endOfDay);
    }
}