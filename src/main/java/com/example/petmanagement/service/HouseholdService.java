package com.example.petmanagement.service;

import com.example.petmanagement.entity.Household;
import java.util.List;

public interface HouseholdService {
    Household createHousehold(Household household);
    List<Household> getAllHouseholds();
    Household getHouseholdByEircode(String eircode);
    Household getHouseholdByEircodeWithPets(String eircode);
    Household updateHousehold(Household household);
    void deleteHouseholdByEircode(String eircode);
    List<Household> getHouseholdsWithNoPets();
    List<Household> getOwnerOccupiedHouseholds();
    long getNumberOfEmptyHouseholds();
    long getNumberOfFullHouseholds();
}
