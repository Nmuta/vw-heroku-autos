
package com.galvanize.autos.controller;

import com.galvanize.autos.AutosService;
import com.galvanize.autos.AutosList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutosController {

    AutosService autosService;

    public AutosController(AutosService autosService) {
        this.autosService = autosService;
    }
    @GetMapping("/api/autos")
    public AutosList getAutos() {

        return autosService.getAutos();
    }

}