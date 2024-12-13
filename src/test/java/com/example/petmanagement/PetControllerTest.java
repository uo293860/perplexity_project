package com.example.petmanagement;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.petmanagement.controller.PetController;
import com.example.petmanagement.entity.Pet;
import com.example.petmanagement.service.PetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PetController.class)
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @Test
    @WithMockUser(roles = "ADMIN") // or "USER" based on your needs
    public void testCreatePet() throws Exception {
        Pet pet = new Pet();
        pet.setName("Buddy");

        when(petService.createPet(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(post("/api/pets")
                        .contentType("application/json")
                        .content("{\"name\":\"Buddy\", \"animalType\":\"Dog\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"));

        verify(petService).createPet(any(Pet.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN") // or "USER" based on your needs
    public void testUpdatePet() throws Exception {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");

        when(petService.updatePet(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(put("/api/pets/1")
                        .contentType("application/json")
                        .content("{\"name\":\"Max\", \"animalType\":\"Dog\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Max"));

        verify(petService).updatePet(any(Pet.class));
    }
}


