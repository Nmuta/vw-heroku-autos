package com.galvanize.autos.service;

import com.galvanize.autos.model.Automobile;
import com.galvanize.autos.model.AutosList;
import org.springframework.stereotype.Service;

@Service
public class AutosService {

    public AutosList getAutos() {
        return null;
    }

    public AutosList getAutos(String color, String make) {
        return null;
    }

    public AutosList getAutos(String colorOrMarke) {
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
