package com.example.petmanagement.repository;

import com.example.petmanagement.dto.PetNameBreedDto;
import com.example.petmanagement.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    void deleteByNameIgnoreCase(String name);
    List<Pet> findByAnimalTypeIgnoreCase(String animalType);
    List<Pet> findByBreedIgnoreCaseOrderByAgeAsc(String breed);

    @Query("SELECT new com.example.petmanagement.dto.PetNameBreedDto(p.name, p.animalType, p.breed) FROM Pet p")
    List<PetNameBreedDto> findAllPetNameAndBreed();

    @Query("SELECT AVG(p.age) FROM Pet p")
    Double findAverageAge();

    @Query("SELECT MAX(p.age) FROM Pet p")
    Integer findOldestAge();
}
