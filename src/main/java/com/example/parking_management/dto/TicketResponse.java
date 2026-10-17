package com.example.parking_management.dto;

import com.example.parking_management.model.VehicleType;
import java.time.LocalDateTime;

public class TicketResponse {
    private Long id;
    private String licensePlate;
    private VehicleType vehicleType;
    private String zoneName;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    public TicketResponse(Long id, String licensePlate, VehicleType vehicleType, String zoneName, LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.zoneName = zoneName;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }

    // Getters and Setters (Bạn tự sinh Get/Set cho các trường này nhé)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public VehicleType getVehicleType() { return vehicleType; }
    public void setVehicleType(VehicleType vehicleType) { this.vehicleType = vehicleType; }
    public String getZoneName() { return zoneName; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }
    public LocalDateTime getCheckInTime() { return checkInTime; }
    public void setCheckInTime(LocalDateTime checkInTime) { this.checkInTime = checkInTime; }
    public LocalDateTime getCheckOutTime() { return checkOutTime; }
    public void setCheckOutTime(LocalDateTime checkOutTime) { this.checkOutTime = checkOutTime; }
}