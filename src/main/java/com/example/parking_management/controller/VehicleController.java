package com.example.parking_management.controller;

import com.example.parking_management.dto.ApiResponse;
import com.example.parking_management.dto.PageResponse;
import com.example.parking_management.dto.VehicleCreateRequest;
import com.example.parking_management.dto.VehicleResponse;
import com.example.parking_management.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<ApiResponse<VehicleResponse>> createVehicle(@RequestBody VehicleCreateRequest req) {
        VehicleResponse response = vehicleService.createVehicle(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Vehicle created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<VehicleResponse>>> getAllVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String direction,
            @RequestParam(required = false) String keyword) {

        PageResponse<VehicleResponse> resultPage = vehicleService.getPagedVehicles(page, size, sortBy, direction, keyword);

        return ResponseEntity.ok(new ApiResponse<>(true, "Vehicles fetched successfully", resultPage));
    }
}