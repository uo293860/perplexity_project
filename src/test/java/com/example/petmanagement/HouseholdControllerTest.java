package com.example.petmanagement;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.petmanagement.controller.HouseholdController;
import com.example.petmanagement.entity.Household;
import com.example.petmanagement.service.HouseholdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HouseholdController.class)
public class HouseholdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseholdService householdService;

    @Test
    @WithMockUser(roles = "ADMIN") // or "USER" based on your needs
    public void testCreateHousehold() throws Exception {
        Household household = new Household();
        household.setEircode("D01AB12");

        when(householdService.createHousehold(any(Household.class))).thenReturn(household);

        mockMvc.perform(post("/api/households")
                        .contentType("application/json")
                        .content("{\"eircode\":\"D01AB12\", \"numberOfOccupants\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eircode").value("D01AB12"));

        verify(householdService).createHousehold(any(Household.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN") // or "USER" based on your needs
    public void testUpdateHousehold() throws Exception {
        Household household = new Household();
        household.setEircode("D01AB12");

        when(householdService.updateHousehold(any(Household.class))).thenReturn(household);

        mockMvc.perform(put("/api/households/D01AB12")
                        .contentType("application/json")
                        .content("{\"numberOfOccupants\":3}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfOccupants").value(3));

        verify(householdService).updateHousehold(any(Household.class));
    }
}

