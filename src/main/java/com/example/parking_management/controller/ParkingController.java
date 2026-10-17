package com.example.parking_management.controller;

import com.example.parking_management.dto.ApiResponse;
import com.example.parking_management.dto.TicketRequest;
import com.example.parking_management.dto.TicketResponse;
import com.example.parking_management.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tickets")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @PostMapping("/check-in")
    public ResponseEntity<ApiResponse<TicketResponse>> checkIn(@RequestBody TicketRequest req) {
        TicketResponse response = parkingService.checkIn(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Checked in successfully", response));
    }

    @PutMapping("/check-out/{vehicleId}")
    public ResponseEntity<ApiResponse<TicketResponse>> checkOut(@PathVariable Long vehicleId) {
        TicketResponse response = parkingService.checkOut(vehicleId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Checked out successfully", response));
    }
}