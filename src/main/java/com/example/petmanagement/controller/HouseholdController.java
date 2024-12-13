package com.example.petmanagement.controller;

import com.example.petmanagement.dto.HouseholdStatistics;
import com.example.petmanagement.entity.Household;
import com.example.petmanagement.service.HouseholdService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/households")
public class HouseholdController {

    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Household> createHousehold(@Valid @RequestBody Household household) {
        return new ResponseEntity<>(householdService.createHousehold(household), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Household>> getAllHouseholds() {
        return ResponseEntity.ok(householdService.getAllHouseholds());
    }

    @GetMapping("/{eircode}")
    public ResponseEntity<Household> getHouseholdByEircode(@PathVariable String eircode) {
        return ResponseEntity.ok(householdService.getHouseholdByEircode(eircode));
    }

    @GetMapping("/{eircode}/with-pets")
    public ResponseEntity<Household> getHouseholdByEircodeWithPets(@PathVariable String eircode) {
        return ResponseEntity.ok(householdService.getHouseholdByEircodeWithPets(eircode));
    }

    @PutMapping("/{eircode}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Household> updateHousehold(@PathVariable String eircode, @Valid @RequestBody Household household) {
        household.setEircode(eircode);
        return ResponseEntity.ok(householdService.updateHousehold(household));
    }

    @DeleteMapping("/{eircode}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteHouseholdByEircode(@PathVariable String eircode) {
        householdService.deleteHouseholdByEircode(eircode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/no-pets")
    public ResponseEntity<List<Household>> getHouseholdsWithNoPets() {
        return ResponseEntity.ok(householdService.getHouseholdsWithNoPets());
    }

    @GetMapping("/owner-occupied")
    public ResponseEntity<List<Household>> getOwnerOccupiedHouseholds() {
        return ResponseEntity.ok(householdService.getOwnerOccupiedHouseholds());
    }

    @GetMapping("/statistics")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public HouseholdStatistics getHouseholdStatistics() {
        return new HouseholdStatistics(householdService.getNumberOfEmptyHouseholds(), householdService.getNumberOfFullHouseholds());
    }
}

