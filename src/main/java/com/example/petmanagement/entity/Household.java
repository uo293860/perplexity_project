package com.example.petmanagement.entity;

//import jakarta.persistence.*;
import com.example.petmanagement.controller.PetManagementGraphQLResolver;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Household {
    @Id
    private String eircode;
    private int numberOfOccupants;
    private int maxNumberOfOccupants;
    private boolean isOwnerOccupied;

    @JsonManagedReference
    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL)
    private List<Pet> pets;

    public Household(PetManagementGraphQLResolver.HouseholdInput input) {
        this.eircode = input.eircode();
        this.numberOfOccupants = input.numberOfOccupants();
        this.maxNumberOfOccupants = input.maxNumberOfOccupants();
        this.isOwnerOccupied = input.isOwnerOccupied();
    }
}
