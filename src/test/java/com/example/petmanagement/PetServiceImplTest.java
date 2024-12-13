package com.example.petmanagement;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.example.petmanagement.entity.Pet;
import com.example.petmanagement.repository.PetRepository;
import com.example.petmanagement.service.PetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class PetServiceImplTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePet() {
        Pet pet = new Pet();
        pet.setName("Buddy");
        pet.setAnimalType("Dog");

        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        Pet createdPet = petService.createPet(pet);

        assertThat(createdPet.getName()).isEqualTo("Buddy");
        verify(petRepository).save(pet);
    }

    @Test
    public void testUpdatePet() {
        Pet existingPet = new Pet();
        existingPet.setId(1L);
        existingPet.setName("Buddy");

        when(petRepository.findById(1L)).thenReturn(Optional.of(existingPet));
        when(petRepository.save(any(Pet.class))).thenReturn(existingPet);

        existingPet.setName("Max");
        Pet updatedPet = petService.updatePet(existingPet);

        assertThat(updatedPet.getName()).isEqualTo("Max");
        verify(petRepository).save(existingPet);
    }
}

