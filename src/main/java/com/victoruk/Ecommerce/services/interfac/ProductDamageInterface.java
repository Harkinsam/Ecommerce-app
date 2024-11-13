package com.victoruk.Ecommerce.services.interfac;

import com.victoruk.Ecommerce.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ProductDamageInterface {




    Response addDamagedProduct(String quantityDamaged,  String reason, MultipartFile photo);


    Response updateDamagedProduct(Long productDamageId, String quantityDamaged, LocalDate damageDate, String reason, MultipartFile photo);

    Response getDamagedProductById(Long id);

    Response getAllDamagedProduct();

}
