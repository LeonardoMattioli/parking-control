package com.api.parking_control.repositories;

import com.api.parking_control.models.ParkingSpotModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParkingSpotRepository  extends JpaRepository<ParkingSpotModel, UUID> {
    boolean existsByparkingSpotNumber(String parkingSpotNumber);
    boolean existsBylicensePlateCar(String licensePlateCar);
    boolean existsByApartmentAndBlock(String apartment, String block);
}
