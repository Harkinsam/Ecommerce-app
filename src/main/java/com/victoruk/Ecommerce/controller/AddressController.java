package com.victoruk.Ecommerce.controller;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.Address;
import com.victoruk.Ecommerce.services.interfac.AddressInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressInterface addressService;

    @Autowired
    public AddressController(AddressInterface addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/create")
    public Response createAddress(@RequestBody Address address) {
        return addressService.createAddress(address);
    }

    @PutMapping("/update/{id}")
    public Response updateAddress(
            @PathVariable("id") Long addressId,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String country) {
        return addressService.updateAddress(addressId, street, city, state, country);
    }
}
