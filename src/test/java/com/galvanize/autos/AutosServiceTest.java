package com.galvanize.autos;

import com.galvanize.autos.exceptions.AutoNotFoundException;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
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
    void getAutos_oneParam_returnsList() {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang", "AABBCC");
        when(autosRepository.findByColorOrMakeContains(isNull(), anyString()))
                .thenReturn(Arrays.asList(automobile));
        AutosList autosList = autosService.getAutosByColorOrMake(automobile.getColor(), automobile.getMake());
        assertThat(autosList).isNotNull();
        assertThat(autosList.isEmpty()).isFalse();

    }

    @Test
    void addAuto_valid_returnsAuto() {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang", "AABBCC");
        when(autosRepository.save(any(Automobile.class)))
                .thenReturn(automobile);
        Automobile auto = autosService.addAuto(automobile);
        assertThat(auto).isNotNull();
        assertThat(auto.getMake()).isEqualTo("Ford");

    }
    @Test
    void getAuto_withVin_returnsAuto() {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang", "AABBCC");
        when(autosRepository.findByVin(anyString()))
                .thenReturn(java.util.Optional.of(automobile));
        Automobile auto = autosService.getAutoByVin(automobile.getVin());
        assertThat(auto).isNotNull();
        assertThat(auto.getVin()).isEqualTo(automobile.getVin());

    }

    @Test
    void updateAuto_returnsAuto() {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang", "AABBCC");
        automobile.setColor("RED");
        when(autosRepository.findByVin(anyString()))
                .thenReturn(java.util.Optional.of(automobile));
        when(autosRepository.save(any(Automobile.class))).thenReturn(automobile);
        Automobile auto = autosService.updateAuto(automobile.getVin(), "PURPLE", "ANYBODY");
        assertThat(auto).isNotNull();
        assertThat(auto.getVin()).isEqualTo(automobile.getVin());

    }

    @Test
    void deleteAuto_byVin() {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang", "AABBCC");
        automobile.setColor("RED");
        when(autosRepository.findByVin(anyString()))
                .thenReturn(java.util.Optional.of(automobile));

        autosService.deleteAuto(automobile.getVin());

        verify(autosRepository).delete(any(Automobile.class));
    }

    @Test
    void deleteAuto_byVin_notExist() {

        when(autosRepository.findByVin(anyString())).thenReturn(Optional.empty());

        assertThatExceptionOfType(AutoNotFoundException.class)
                .isThrownBy(() -> {
                    autosService.deleteAuto("NOT-EXISTS-VIN");
                });

    }
}
