package com.api.parking_control.controllers;

import com.api.parking_control.dtos.ParkingSpotDto;
import com.api.parking_control.models.ParkingSpotModel;
import com.api.parking_control.services.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    @Autowired
    private ParkingSpotService parkingSpotService;

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        if (parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.parkingSpotNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Parking spot already exists");
        }

        if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.licensePlateCar())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("License plate already exists");
        }

        if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.apartment(), parkingSpotDto.block())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Apartment and block already exists");
        }

        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
    }

    @GetMapping
    public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpot(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.getAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getOneParkingLot(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpotModel> park = parkingSpotService.getOnePark(id);
        if(park.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(park.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteParking(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpotModel> park = parkingSpotService.getOnePark(id);
        if(park.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        parkingSpotService.delete(park.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully");
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id, @Valid @RequestBody ParkingSpotDto parkingSpotDto) {
        Optional<ParkingSpotModel> park = parkingSpotService.getOnePark(id);
        if(park.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        var parkingSpot = new ParkingSpotModel();
        parkingSpot.setParkingSpotNumber(parkingSpotDto.parkingSpotNumber());
        parkingSpot.setApartment(parkingSpotDto.apartment());
        parkingSpot.setBlock(parkingSpotDto.block());
        parkingSpot.setBrandCar(parkingSpotDto.brandCar());
        parkingSpot.setColorCar(parkingSpotDto.colorCar());
        parkingSpot.setLicensePlateCar(parkingSpotDto.licensePlateCar());
        parkingSpot.setModelCar(parkingSpotDto.modelCar());
        parkingSpot.setResponsibleName(parkingSpotDto.responsibleName());
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpot));
    }

}
