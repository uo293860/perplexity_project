package com.example.petmanagement.controller;

import com.example.petmanagement.dto.HouseholdStatistics;
import com.example.petmanagement.dto.PetNameBreedDto;
import com.example.petmanagement.dto.PetStatistics;
import com.example.petmanagement.entity.Household;
import com.example.petmanagement.entity.Pet;
import com.example.petmanagement.service.HouseholdService;
import com.example.petmanagement.service.PetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class PetManagementGraphQLResolver {

    private final PetService petService;
    private final HouseholdService householdService;

    // Pet Queries
    @QueryMapping
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @QueryMapping
    public Pet getPetById(@Argument Long id) {
        return petService.getPetById(id);
    }

    @QueryMapping
    public List<Pet> getPetsByAnimalType(@Argument String animalType) {
        return petService.findPetsByAnimalType(animalType);
    }

    @QueryMapping
    public List<Pet> getPetsByBreed(@Argument String breed) {
        return petService.findPetsByBreed(breed);
    }

    @QueryMapping
    public List<PetNameBreedDto> getPetNameAndBreed() {
        return petService.getPetNameAndBreed();
    }

    @QueryMapping
    public PetStatistics getPetStatistics() {
        return new PetStatistics(
                petService.getAverageAge(),
                petService.getOldestAge()
        );
    }

    // Household Queries
    @QueryMapping
    public List<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @QueryMapping
    public Household getHouseholdByEircode(@Argument String eircode) {
        return householdService.getHouseholdByEircode(eircode);
    }

    @QueryMapping
    public Household getHouseholdByEircodeWithPets(@Argument String eircode) {
        return householdService.getHouseholdByEircodeWithPets(eircode);
    }

    @QueryMapping
    public List<Household> getHouseholdsWithNoPets() {
        return householdService.getHouseholdsWithNoPets();
    }

    @QueryMapping
    public List<Household> getOwnerOccupiedHouseholds() {
        return householdService.getOwnerOccupiedHouseholds();
    }

    @QueryMapping
    public HouseholdStatistics getHouseholdStatistics() {
        return new HouseholdStatistics(
                householdService.getNumberOfEmptyHouseholds(),
                householdService.getNumberOfFullHouseholds()
        );
    }

    // Pet Mutations
    @MutationMapping
    public Pet createPet(@Argument PetInput input) {
        Household household = householdService.getHouseholdByEircode(input.householdEircode());
        Pet pet = new Pet(input, household);
        // Map input to pet object
        return petService.createPet(pet);
    }

    @MutationMapping
    public Pet updatePet(@Argument Long id, @Argument PetInput input) {
        Pet existingPet = petService.getPetById(id);
        if (existingPet == null) {
            throw new RuntimeException("Pet not found with id: " + id);
        }

        existingPet.setName(input.name());
        existingPet.setAnimalType(input.animalType());
        existingPet.setBreed(input.breed());
        existingPet.setAge(input.age());

        if (input.householdEircode() != null) {
            Household household = householdService.getHouseholdByEircode(input.householdEircode());
            existingPet.setHousehold(household);
        }

        return petService.updatePet(existingPet);
    }

    @MutationMapping
    public boolean deletePet(@Argument Long id) {
        petService.deletePetById(id);
        return true;
    }

    @MutationMapping
    public int deletePetsByName(@Argument String name) {
        petService.deletePetsByName(name);
        return 1; // Return the number of deleted pets
    }

    // Household Mutations
    @MutationMapping
    public Household createHousehold(@Argument HouseholdInput input) {
        Household household = new Household(input);
        // Map input to household object
        return householdService.createHousehold(household);
    }

    @MutationMapping
    public Household updateHousehold(@Argument @Valid HouseholdInput input) {
        Household existingHousehold = householdService.getHouseholdByEircode(input.eircode());
        if (existingHousehold == null) {
            throw new RuntimeException("Household not found with eircode: " + input.eircode());
        }

        existingHousehold.setNumberOfOccupants(input.numberOfOccupants());
        existingHousehold.setMaxNumberOfOccupants(input.maxNumberOfOccupants());
        existingHousehold.setOwnerOccupied(input.isOwnerOccupied());

        return householdService.updateHousehold(existingHousehold);
    }

    @MutationMapping
    public boolean deleteHousehold(@Argument String eircode) {
        householdService.deleteHouseholdByEircode(eircode);
        return true;
    }

    // Input classes (can be separate files if preferred)
    public record PetInput(
            @NotBlank(message = "Name cannot be blank")
            String name,

            @NotBlank(message = "Animal type cannot be blank")
            String animalType,

            @NotBlank(message = "Breed cannot be blank")
            String breed,

            @Min(value = 0, message = "Age must be a non-negative number")
            int age,

            String householdEircode // Optional, so no validation here
    ) {
    }

    public record HouseholdInput(
            @NotBlank(message = "Eircode cannot be blank")
            String eircode,

            @Min(value = 0, message = "Number of occupants must be non-negative")
            int numberOfOccupants,

            @Min(value = 0, message = "Maximum number of occupants must be non-negative")
            int maxNumberOfOccupants,

            boolean isOwnerOccupied
    ) {
    }
}

