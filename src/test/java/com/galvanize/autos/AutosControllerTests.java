package com.galvanize.autos;

public class AutosControllerTests {

//GET
    // GET: /api/autos returns list of all autos in db
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
