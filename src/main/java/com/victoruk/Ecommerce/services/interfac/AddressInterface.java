package com.victoruk.Ecommerce.services.interfac;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.Address;

public interface AddressInterface {

    Response createAddress(Address address);

    Response updateAddress(Long addressiD, String address, String city, String state, String country );




    }
