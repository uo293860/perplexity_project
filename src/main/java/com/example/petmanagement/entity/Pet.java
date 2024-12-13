package com.example.petmanagement.entity;

//import jakarta.persistence.*;
import com.example.petmanagement.controller.PetManagementGraphQLResolver;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String animalType;
    private String breed;
    private int age;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "eircode", referencedColumnName = "eircode")
    private Household household;

    public Pet(PetManagementGraphQLResolver.PetInput input, Household household) {
        this.name = input.name();
        this.animalType = input.animalType();
        this.breed = input.breed();
        this.age = input.age();
        this.household = household;
    }
}
