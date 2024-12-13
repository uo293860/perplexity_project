package com.example.petmanagement.service;

import com.example.petmanagement.entity.Household;
import com.example.petmanagement.repository.HouseholdRepository;
import com.example.petmanagement.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HouseholdServiceImpl implements HouseholdService {

    private final HouseholdRepository householdRepository;

    public HouseholdServiceImpl(HouseholdRepository householdRepository) {
        this.householdRepository = householdRepository;
    }

    @Override
    public Household createHousehold(Household household) {
        if (household.getEircode() == null || household.getEircode().trim().isEmpty()) {
            throw new InvalidHouseholdDataException("Eircode cannot be empty");
        }
        if (household.getNumberOfOccupants() < 0 || household.getMaxNumberOfOccupants() < 0) {
            throw new InvalidHouseholdDataException("Number of occupants cannot be negative");
        }
        if (household.getNumberOfOccupants() > household.getMaxNumberOfOccupants()) {
            throw new InvalidHouseholdDataException("Number of occupants cannot exceed maximum occupancy");
        }
        return householdRepository.save(household);
    }

    @Override
    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    @Override
    public Household getHouseholdByEircode(String eircode) {
        return householdRepository.findById(eircode)
                .orElseThrow(() -> new HouseholdNotFoundException("Household not found with eircode: " + eircode));
    }

    @Override
    public Household getHouseholdByEircodeWithPets(String eircode) {
        return householdRepository.findByEircodeWithPets(eircode)
                .orElseThrow(() -> new HouseholdNotFoundException("Household not found with eircode: " + eircode));
    }

    @Override
    public Household updateHousehold(Household household) {
        if (householdRepository.findByEircodeWithPets(household.getEircode()).isEmpty()) {
            throw new HouseholdNotFoundException("Household not found with eircode: " + household.getEircode());
        }
        if (household.getNumberOfOccupants() < 0 || household.getMaxNumberOfOccupants() < 0) {
            throw new InvalidHouseholdDataException("Number of occupants cannot be negative");
        }
        if (household.getNumberOfOccupants() > household.getMaxNumberOfOccupants()) {
            throw new InvalidHouseholdDataException("Number of occupants cannot exceed maximum occupancy");
        }
        return householdRepository.save(household);
    }

    @Override
    @Transactional
    public void deleteHouseholdByEircode(String eircode) {
        Household household = getHouseholdByEircode(eircode);
        // This will cascade delete all associated pets
        householdRepository.delete(household);
    }

    @Override
    public List<Household> getHouseholdsWithNoPets() {
        return householdRepository.findByPetsIsEmpty();
    }

    @Override
    public List<Household> getOwnerOccupiedHouseholds() {
        return householdRepository.findByIsOwnerOccupiedTrue();
    }

    @Override
    public long getNumberOfEmptyHouseholds() {
        return householdRepository.countEmptyHouseholds();
    }

    @Override
    public long getNumberOfFullHouseholds() {
        return householdRepository.countFullHouseholds();
    }
}
