package com.galvanize.autos;

import com.galvanize.autos.controller.AutosController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AutosController.class)
public class AutosControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AutosService autosService;

    //GET
    // GET: /api/autos returns list of all autos in db
    @Test
    void getAuto_noParams_exists_returnsAutosList() throws Exception{
        // Arrange
        List<Automobile> automobiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            automobiles.add(new Automobile(1967+i, "Ford", "Mustang", "AABB" + i));
        }
        when(autosService.getAutos()).thenReturn(new AutosList(automobiles));
        // Act
        mockMvc.perform(MockMvcRequestBuilders.get("/api/autos"))
                .andDo(print())
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automobiles", hasSize(5)));

    }

    // GET: /api/autos returns no autos in db (204) no content
    // GET: /api/autos?color=blue returns list of all blue autos in db
    // GET: /api/autos?make=volkswagen returns list of all vw autos in db
    // GET: /api/autos?make=volkswagen?color=blue returns list of all blue vw in db

// POST
    // POST: /api/autos returns a new created auto
    // POST: /api/autos returns error due to a bad request (400)

//GET a specific auto
    // GET: /api/autos/{vin} returns the Auto that matches the vin
    // GET: /api/autos/{vin} returns no auto found (204)


//PATCH
    //PATCH: /api/autos/{vin} returns the patched auto
    //PATCH: /api/autos/{vin} returns no auto found (204)
    //PATCH: /api/autos/{vin} returns error message (400) due to bad request

// DELETE
    //DELETE: /api/autos/{vin} return delete request successfully (200)
    //DELETE: /api/autos/{vin} return delete no auto found (204)



}
