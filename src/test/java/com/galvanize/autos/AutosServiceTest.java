package com.galvanize.autos;

import com.galvanize.autos.model.Automobile;
import com.galvanize.autos.model.AutosList;
import com.galvanize.autos.repositories.AutosRepository;
import com.galvanize.autos.service.AutosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutosServiceTest {

    private AutosService autosService;

    @Mock
    AutosRepository autosRepository;

    @BeforeEach
    void setUp() {
        autosService = new AutosService(autosRepository);
    }

    @Test
    void getAutos_noArgs_returnsList() {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang", "AABBCC");
        when(autosRepository.findAll()).thenReturn(Arrays.asList(automobile));
        AutosList autosList = autosService.getAutos();
        assertThat(autosList).isNotNull();
        assertThat(autosList.isEmpty()).isFalse();
    }

    @Test
    void getAutos_search_returnsList() {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang", "AABBCC");
        automobile.setColor("RED");
        when(autosRepository.findByColorContainsAndMakeContains(anyString(), anyString()))
                .thenReturn(Arrays.asList(automobile));
        AutosList autosList = autosService.getAutos("RED", "Ford");
        assertThat(autosList).isNotNull();
        assertThat(autosList.isEmpty()).isFalse();

    }

    @Test
    void testGetAutos1() {
    }

    @Test
    void addAuto() {
    }

    @Test
    void getAutoByVin() {
    }

    @Test
    void updateAuto() {
    }

    @Test
    void deleteAuto() {
    }
}
