package com.victoruk.Ecommerce.services.interfac;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.Discount;


public interface DiscountServiceInterface {


    Response createDiscount(Discount discount);

}
