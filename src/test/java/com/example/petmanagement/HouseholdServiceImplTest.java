package com.example.petmanagement;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.example.petmanagement.entity.Household;
import com.example.petmanagement.repository.HouseholdRepository;
import com.example.petmanagement.service.HouseholdServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class HouseholdServiceImplTest {

    @Mock
    private HouseholdRepository householdRepository;

    @InjectMocks
    private HouseholdServiceImpl householdService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateHousehold() {
        Household household = new Household();
        household.setEircode("D01AB12");

        when(householdRepository.save(any(Household.class))).thenReturn(household);

        Household createdHousehold = householdService.createHousehold(household);

        assertThat(createdHousehold.getEircode()).isEqualTo("D01AB12");
        verify(householdRepository).save(household);
    }

    @Test
    public void testUpdateHousehold() {
        Household existingHousehold = new Household();
        existingHousehold.setEircode("D01AB12");

        when(householdRepository.findByEircodeWithPets("D01AB12")).thenReturn(Optional.of(existingHousehold));
        when(householdRepository.save(any(Household.class))).thenReturn(existingHousehold);

        existingHousehold.setNumberOfOccupants(3);
        existingHousehold.setMaxNumberOfOccupants(10);
        Household updatedHousehold = householdService.updateHousehold(existingHousehold);

        assertThat(updatedHousehold.getNumberOfOccupants()).isEqualTo(3);
        verify(householdRepository).save(existingHousehold);
    }
}

