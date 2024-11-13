package com.victoruk.Ecommerce.services.impli;

import com.victoruk.Ecommerce.Exception.MyException;
import com.victoruk.Ecommerce.Mapper.Mapper;
import com.victoruk.Ecommerce.dto.AddressDTO;
import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.Address;
import com.victoruk.Ecommerce.repository.AddressReository;
import com.victoruk.Ecommerce.services.interfac.AddressInterface;
import org.springframework.stereotype.Service;

@Service
public class AddressService implements AddressInterface {

    private final AddressReository addressReository;

    public AddressService(AddressReository addressReository) {
        this.addressReository = addressReository;
    }


    @Override
    public Response createAddress(Address address) {

        Response response = new Response();

        try {
            Address savedaddress = addressReository.save(address);
            AddressDTO addressDTO = Mapper.mapAddressEntityToAddressDTO(savedaddress);
            response.setStatusCode(200);
            response.setMessage("success");
            response.setData("Created Address", addressDTO);
            response.setData("Address" , addressDTO);
        }catch (MyException e){
            response.setStatusCode(400);
            response.setMessage("Invalid credential");

        }catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("An Error occurred please try again: "  + e.getMessage());
        }


        return response;
    }

    @Override
    public Response updateAddress(Long addressiD, String street, String city, String state, String country) {
        Response response = new Response();

        try {

            Address address = addressReository.findById(addressiD).orElseThrow(() -> new MyException("Address not found: "  + addressiD));

            if (street != null) address.setStreet(street);
            if (city != null) address.setCity(city);
            if (state != null) address.setState(state);
            if (country != null) address.setCountry(country);

            Address updatedAddress = addressReository.save(address);
            AddressDTO addressDTO = Mapper.mapAddressEntityToAddressDTO(updatedAddress);
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setData("Updated Address", addressDTO);
        }catch (MyException e){
            response.setStatusCode(400);
            response.setMessage("Invalid credential");

        }catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("An Error occurred please try again: "  + e.getMessage());
        }

        return response;
    }

}



