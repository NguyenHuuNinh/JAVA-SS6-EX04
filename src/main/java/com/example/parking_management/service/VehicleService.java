package com.example.parking_management.service;

import com.example.parking_management.dto.PageResponse;
import com.example.parking_management.dto.VehicleCreateRequest;
import com.example.parking_management.dto.VehicleResponse;
import com.example.parking_management.model.Vehicle;
import com.example.parking_management.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public VehicleResponse createVehicle(VehicleCreateRequest req) {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(req.getLicensePlate());
        vehicle.setColor(req.getColor());
        vehicle.setType(req.getType());

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return new VehicleResponse(savedVehicle.getId(), savedVehicle.getLicensePlate(), savedVehicle.getColor(), savedVehicle.getType());
    }

    public PageResponse<VehicleResponse> getPagedVehicles(int page, int size, String sortBy, String direction, String keyword) {
        if (page < 0) page = 0;

        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.trim().isEmpty() && direction != null && !direction.trim().isEmpty()) {
            Sort.Direction dir = direction.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
            sort = Sort.by(dir, sortBy);
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<VehicleResponse> vehiclePage = vehicleRepository.findAllByKeyword(keyword, pageable);

        return new PageResponse<>(
                vehiclePage.getContent(),
                vehiclePage.getNumber(),
                vehiclePage.getSize(),
                vehiclePage.getTotalElements(),
                vehiclePage.getTotalPages(),
                vehiclePage.isLast()
        );
    }
}