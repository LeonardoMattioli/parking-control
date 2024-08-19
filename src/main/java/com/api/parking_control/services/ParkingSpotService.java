package com.api.parking_control.services;

import com.api.parking_control.models.ParkingSpotModel;
import com.api.parking_control.repositories.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSpotService {
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return parkingSpotRepository.save(parkingSpotModel);
    }

    public boolean existsByParkingSpotNumber(String existParkingSpotNumber) {
        return parkingSpotRepository.existsByparkingSpotNumber(existParkingSpotNumber);
    }

    public boolean existsByLicensePlateCar(String existsLicensePlateCar) {
        return parkingSpotRepository.existsBylicensePlateCar(existsLicensePlateCar);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
    }

    public List<ParkingSpotModel> getAll() {
        return parkingSpotRepository.findAll();
    }

    public Optional<ParkingSpotModel> getOnePark(UUID id) {
        return parkingSpotRepository.findById(id);
    }
    @Transactional
    public void delete(UUID id) {
        parkingSpotRepository.deleteById(id);
    }
}
