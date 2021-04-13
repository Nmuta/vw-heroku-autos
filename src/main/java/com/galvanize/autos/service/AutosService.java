package com.galvanize.autos.service;

import com.galvanize.autos.model.Automobile;
import com.galvanize.autos.model.AutosList;
import com.galvanize.autos.repositories.AutosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public AutosList getAutosByColorOrMake(String color, String make) {
        List<Automobile> automobiles = autosRepository.findByColorOrMakeContains(color, make);
        return  !automobiles.isEmpty() ? new AutosList(automobiles) : null;
    }

    public Automobile addAuto(Automobile auto) {
        return autosRepository.save(auto);
    }

    public Automobile getAutoByVin(String vin) {
        return autosRepository.findByVin(vin).orElse(null);

    }

    public Automobile updateAuto(String vin, String color, String owner) {
        Optional<Automobile> oAuto = autosRepository.findByVin(vin);
        if (oAuto.isPresent()) {
            oAuto.get().setColor(color);
            oAuto.get().setOwner(owner);
            return autosRepository.save(oAuto.get());
        }
        return null;
    }

    public void deleteAuto(String vin) {

    }
}
