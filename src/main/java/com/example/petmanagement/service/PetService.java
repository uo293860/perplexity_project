package com.example.petmanagement.service;

import com.example.petmanagement.dto.PetNameBreedDto;
import com.example.petmanagement.entity.Pet;

import java.util.List;

public interface PetService {
    Pet createPet(Pet pet);
    List<Pet> getAllPets();
    Pet getPetById(Long id);
    Pet updatePet(Pet pet);
    void deletePetById(Long id);
    void deletePetsByName(String name);
    List<Pet> findPetsByAnimalType(String animalType);
    List<Pet> findPetsByBreed(String breed);
    List<PetNameBreedDto> getPetNameAndBreed();
    Double getAverageAge();
    Integer getOldestAge();
}
