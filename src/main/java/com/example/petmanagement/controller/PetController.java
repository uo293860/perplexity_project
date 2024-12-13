package com.example.petmanagement.controller;

import com.example.petmanagement.dto.PetNameBreedDto;
import com.example.petmanagement.dto.PetStatistics;
import com.example.petmanagement.entity.Pet;
import com.example.petmanagement.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<Pet> createPet(@Valid @RequestBody Pet pet) {
        return new ResponseEntity<>(petService.createPet(pet), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.getPetById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @Valid @RequestBody Pet pet) {
        pet.setId(id);
        return ResponseEntity.ok(petService.updatePet(pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetById(@PathVariable Long id) {
        petService.deletePetById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/name/{name}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePetsByName(@PathVariable String name) {
        petService.deletePetsByName(name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{animalType}")
    public ResponseEntity<List<Pet>> findPetsByAnimalType(@PathVariable String animalType) {
        return ResponseEntity.ok(petService.findPetsByAnimalType(animalType));
    }

    @GetMapping("/breed/{breed}")
    public ResponseEntity<List<Pet>> findPetsByBreed(@PathVariable String breed) {
        return ResponseEntity.ok(petService.findPetsByBreed(breed));
    }

    @GetMapping("/namebreed")
    public ResponseEntity<List<PetNameBreedDto>> getPetNameAndBreed() {
        return ResponseEntity.ok(petService.getPetNameAndBreed());
    }

    @GetMapping("/statistics")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public PetStatistics getPetStatistics() {
        return new PetStatistics(petService.getAverageAge(), petService.getOldestAge());
    }
}

