package com.example.parking_management.dto;

import com.example.parking_management.model.VehicleType;

public class VehicleCreateRequest {
    private String licensePlate;
    private String color;
    private VehicleType type;

    // Getters and Setters
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }
}