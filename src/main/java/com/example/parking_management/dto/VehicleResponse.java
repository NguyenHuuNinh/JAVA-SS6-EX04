package com.example.parking_management.dto;

import com.example.parking_management.model.VehicleType;

public class VehicleResponse {
    private Long id;
    private String licensePlate;
    private String color;
    private VehicleType vehicleType;

    public VehicleResponse(Long id, String licensePlate, String color, VehicleType vehicleType) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.color = color;
        this.vehicleType = vehicleType;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public VehicleType getVehicleType() { return vehicleType; }
    public void setVehicleType(VehicleType vehicleType) { this.vehicleType = vehicleType; }
}