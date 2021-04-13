package com.galvanize.autos.service;

import com.galvanize.autos.model.Automobile;
import com.galvanize.autos.model.AutosList;
import com.galvanize.autos.repositories.AutosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutosService {

    AutosRepository autosRepository;

    public AutosService(AutosRepository autosRepository) {
        this.autosRepository = autosRepository;
    }

    public AutosList getAutos() {
        // Query: select * from autos;
        // Put that in a list
        // Return a new AutosList with the list
        return new AutosList(autosRepository.findAll());

    }

    public AutosList getAutos(String color, String make) {
        List<Automobile> automobiles = autosRepository.findByColorContainsAndMakeContains(color, make);
        return  !automobiles.isEmpty() ? new AutosList(automobiles) : null;

    }

    public AutosList getAutos(String colorOrMake) {
        return null;
    }

    public Automobile addAuto(Automobile auto) {
        return null;
    }

    public Automobile getAutoByVin(String anyString) {
        return null;
    }

    public Automobile updateAuto(String vin, String color, String owner) {
        return null;
    }

    public void deleteAuto(String vin) {

    }
}
