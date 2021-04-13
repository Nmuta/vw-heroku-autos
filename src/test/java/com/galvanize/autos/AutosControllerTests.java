package com.galvanize.autos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AutosController.class)
public class AutosControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AutosService autosService;

    ObjectMapper mapper = new ObjectMapper();

    //GET
    // GET: /api/autos returns list of all autos in db
    @Test
    void getAuto_noParams_exists_returnsAutosList() throws Exception {
        // Arrange
        List<Automobile> automobiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            automobiles.add(new Automobile(1967 + i, "Ford", "Mustang", "AABB" + i));
        }
        when(autosService.getAutos()).thenReturn(new AutosList(automobiles));
        // Act
        mockMvc.perform(get("/api/autos"))
                .andDo(print())
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automobiles", hasSize(5)));
    }

    // GET: /api/autos returns no autos in db (204) no contentv
    @Test
    void getAutos_noParams_none_returnsNoContent() throws Exception {
        // Arrange
        when(autosService.getAutos()).thenReturn(new AutosList());
        // Act
        mockMvc.perform(get("/api/autos"))
                .andDo(print())
                // Assert
                .andExpect(status().isNoContent());
    }

    // GET: /api/autos?make=volkswagen?color=blue returns list of all blue vw in db
    @Test
    void getAutos_searchParams_exists_returnsAutosList() throws Exception {
        List<Automobile> automobiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            automobiles.add(new Automobile(1967 + i, "Ford", "Mustang", "AABB" + i));
        }
        when(autosService.getAutos(anyString(), anyString())).thenReturn(new AutosList(automobiles));
        mockMvc.perform(get("/api/autos?color=RED&make=Ford"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automobiles", hasSize(5)));

    }

    // GET: /api/autos?color=red returns list of all blue autos in db
    @Test
    void getAutos_searchParamColor_exists_returnsAutosList() throws Exception {
        List<Automobile> automobiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            automobiles.add(new Automobile(1967 + i, "Ford", "Mustang", "AABB" + i));
        }
        when(autosService.getAutos(anyString())).thenReturn(new AutosList(automobiles));
        mockMvc.perform(get("/api/autos?color=RED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automobiles", hasSize(5)));
    }

    // GET: /api/autos?make=Ford returns list of all vw autos in db
    @Test
    void getAutos_searchParamMake_exists_returnsAutosList() throws Exception {
        List<Automobile> automobiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            automobiles.add(new Automobile(1967 + i, "Ford", "Mustang", "AABB" + i));
        }
        when(autosService.getAutos(anyString())).thenReturn(new AutosList(automobiles));
        mockMvc.perform(get("/api/autos?make=Ford"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automobiles", hasSize(5)));
    }

    // POST
    // POST: /api/autos returns a new created auto
    @Test
    void addAuto_valid_returnsAuto() throws Exception {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang", "AABBCC");
        when(autosService.addAuto(any(Automobile.class))).thenReturn(automobile);
        // String json = "{\"year\":1967,\"make\":\"Ford\",\"model\":\"Mustang\",\"color\":null,\"owner\":null,\"vin\":\"AABBCC\"}";
        mockMvc.perform(post("/api/autos").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(automobile)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("make").value("Ford"));

    }

    // POST: /api/autos returns error due to a bad request (400)
    @Test
    void addAuto_badRequest_returns400() throws Exception {
        when(autosService.addAuto(any(Automobile.class))).thenThrow(InvalidAutoException.class);
        String json = "{\"year\":1967,\"make\":\"Ford\",\"model\":\"Mustang\",\"color\":null,\"owner\":null,\"vin\":\"AABBCC\"}";
        mockMvc.perform(post("/api/autos").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    //GET a specific auto
    // GET: /api/autos/{vin} returns the Auto that matches the vin
    @Test
    void getAuto_withVin_returnsAuto() throws Exception {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang", "AABBCC");
        when(autosService.getAutoByVin(anyString())).thenReturn(automobile);
        mockMvc.perform(get("/api/autos/" + automobile.getVin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("vin").value(automobile.getVin()));
    }

    // GET: /api/autos/{vin} returns no auto found (204)
    @Test
    void getAuto_withVin_none_returnsNoContent() throws Exception {
        when(autosService.getAutoByVin(anyString())).thenReturn(null);
        mockMvc.perform(get("/api/autos/AB"))
                .andExpect(status().isNoContent());
    }

    //PATCH
    //PATCH: /api/autos/{vin} returns the patched auto
    @Test
    void updateAuto_withObject_returnsAuto() throws Exception {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang", "AABBCC");
        when(autosService.updateAuto(anyString(), anyString(), anyString())).thenReturn(automobile);
        mockMvc.perform(patch("/api/autos/" + automobile.getVin())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"color\":\"RED\",\"owner\":\"Hector\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("color").value("RED"))
                .andExpect(jsonPath("owner").value("Hector"));
    }

    //PATCH: /api/autos/{vin} returns no auto found (204)
    @Test
    void updateAuto_withObject_none_returnsNoContent() throws Exception {
        when(autosService.updateAuto(anyString(), anyString(), anyString())).thenReturn(null);
        mockMvc.perform(patch("/api/autos/AB")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"color\":\"RED\",\"owner\":\"Hector\"}"))
                .andExpect(status().isNoContent());
    }

    //PATCH: /api/autos/{vin} returns error message (400) due to bad request
    @Test
    void updateAuto_badRequest_returns400() throws Exception {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang", "AABBCC");
        when(autosService.updateAuto(any(), any(), any())).thenThrow(InvalidAutoException.class);
        String json = "{\"vin\":\"AABBCC\",\"color\":RED,\"owner\":Hector}";
        mockMvc.perform(patch("/api/autos/" + automobile.getVin()).contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


// DELETE
    //DELETE: /api/autos/{vin} return delete request successfully (200)
    //DELETE: /api/autos/{vin} return delete no auto found (204)


}
