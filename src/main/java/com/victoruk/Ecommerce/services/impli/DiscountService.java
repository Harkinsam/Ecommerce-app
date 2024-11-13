package com.victoruk.Ecommerce.services.impli;

import com.victoruk.Ecommerce.Exception.MyException;
import com.victoruk.Ecommerce.Mapper.Mapper;
import com.victoruk.Ecommerce.dto.DiscountDTO;
import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.Discount;
import com.victoruk.Ecommerce.repository.DiscountRepository;
import com.victoruk.Ecommerce.services.interfac.DiscountServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountService implements DiscountServiceInterface {

    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public Response createDiscount(Discount discount) {
        Response response = new Response();
        try {
            Discount savedDiscount = discountRepository.save(discount);
            response.setMessage("Discount created successfully and is available for products.");
            response.setStatusCode(201);
            response.setData("Discount", savedDiscount);
        } catch (MyException e) {
            response.setStatusCode(400);
            response.setMessage("Error creating discount check discount format: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error creating discount: " + e.getMessage());
        }

        return response;

    }
}