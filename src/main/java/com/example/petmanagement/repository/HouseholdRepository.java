package com.example.petmanagement.repository;

import com.example.petmanagement.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HouseholdRepository extends JpaRepository<Household, String> {
    @Query("SELECT h FROM Household h LEFT JOIN FETCH h.pets WHERE h.eircode = :eircode")
    Optional<Household> findByEircodeWithPets(@Param("eircode") String eircode);

    List<Household> findByPetsIsEmpty();

    List<Household> findByIsOwnerOccupiedTrue();

    @Query("SELECT COUNT(h) FROM Household h WHERE h.numberOfOccupants = 0")
    long countEmptyHouseholds();

    @Query("SELECT COUNT(h) FROM Household h WHERE h.numberOfOccupants = h.maxNumberOfOccupants")
    long countFullHouseholds();
}

