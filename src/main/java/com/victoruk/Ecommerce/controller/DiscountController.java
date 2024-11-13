package com.victoruk.Ecommerce.controller;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.Discount;
import com.victoruk.Ecommerce.services.interfac.DiscountServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    private final DiscountServiceInterface discountService;

    public DiscountController(DiscountServiceInterface discountService) {
        this.discountService = discountService;
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createDiscount(@RequestBody Discount discount) {
        if (discount.getDiscountPercentage() == null) {
            Response response = new Response();
            response.setMessage("Discount percentage is required.");
            response.setStatusCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Response response = discountService.createDiscount(discount);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

}
