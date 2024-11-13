package com.victoruk.Ecommerce.services.impli;

import com.victoruk.Ecommerce.Exception.MyException;
import com.victoruk.Ecommerce.Mapper.Mapper;
import com.victoruk.Ecommerce.cloudService.CloudinaryService;
import com.victoruk.Ecommerce.dto.ProductDamageDTO;
import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.ProductDamage;
import com.victoruk.Ecommerce.repository.productDamageRepository;
import com.victoruk.Ecommerce.services.interfac.ProductDamageInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductDamageService  implements ProductDamageInterface {


    private final productDamageRepository  productDamageRepository;

    private final CloudinaryService cloudinaryService;

    public ProductDamageService(com.victoruk.Ecommerce.repository.productDamageRepository productDamageRepository, CloudinaryService cloudinaryService) {
        this.productDamageRepository = productDamageRepository;
        this.cloudinaryService = cloudinaryService;
    }



    @Override
    public Response addDamagedProduct(String quantityDamaged, String reason, MultipartFile photo) {

        Response response = new Response();
        try {
            // Upload the provided photo to Cloudinary and retrieve the result
            Map<String, Object> uploadResult = cloudinaryService.upload(photo);

            // Extract the URL of the uploaded photo from the result map
            String photoUrl = (String) uploadResult.get("url");


            // create a new product entity to hold damaged product details
            ProductDamage productDamage = new ProductDamage();
            productDamage.setQuantityDamaged(quantityDamaged);
//            productDamage.setDamageDate(damageDate);
            productDamage.setReason(reason);
            productDamage.setPhotoUrl(photoUrl);
            ProductDamage savedproductDamage = productDamageRepository.save(productDamage);
            ProductDamageDTO productDamageDTO = Mapper.mapProductDamageEntityToProductDamageDTo(savedproductDamage);
            response.setMessage("successful");
            response.setStatusCode(200);
            response.setData("ProductDamage", productDamageDTO);

        }catch (MyException e){
            // Handle custom exception
            response.setStatusCode(200);
            response.setMessage("Unable to add DamagedProduct check your details: " + e.getMessage());

        } catch (Exception e) {
            // Handle general exception
            response.setStatusCode(200);
            response.setMessage("An error occured please try again : " + e.getMessage());

        }

        return response;
    }

    @Override
    public Response updateDamagedProduct(Long productDamageId, String quantityDamaged, LocalDate damageDate, String reason, MultipartFile photo) {

        Response response = new Response();

        try {
            // Fetch the existing damaged product entry from the repository by ID
            ProductDamage existingProductDamage = productDamageRepository.findById(productDamageId)
                    .orElseThrow(() -> new MyException("Product Damage not found for the given ID: " + productDamageId));

            // Upload the new photo if provided, else keep the old photo URL
            String photoUrl = null;

            if (photo != null && !photo.isEmpty()) {
                Map<String, Object> uploadResult = cloudinaryService.upload(photo);
                photoUrl = (String) uploadResult.get("url");
            } else {
                photoUrl = existingProductDamage.getPhotoUrl(); // Retain the existing photo URL
            }


            // Update the fields of the existing product damage entry
            existingProductDamage.setQuantityDamaged(quantityDamaged);
            existingProductDamage.setDamageDate(damageDate);
            if (reason != null) existingProductDamage.setReason(reason);
            if (photoUrl != null) existingProductDamage.setPhotoUrl(photoUrl);

            // Save the updated entity
            ProductDamage updatedProductDamage = productDamageRepository.save(existingProductDamage);

            // Map the updated entity to a DTO
            ProductDamageDTO productDamageDTO = Mapper.mapProductDamageEntityToProductDamageDTo(updatedProductDamage);

            // Set response object
            response.setMessage("Product damage updated successfully");
            response.setStatusCode(200);
            response.setData("ProductDamage", productDamageDTO);

        } catch (MyException e) {
            // Handle custom exceptions
            response.setStatusCode(400);
            response.setMessage("Unable to update Damaged Product: " + e.getMessage());

        } catch (Exception e) {
            // Handle general exceptions
            response.setStatusCode(500);
            response.setMessage("An error occurred: " + e.getMessage());
        }

        return response;
    }


    @Override
    public Response getDamagedProductById(Long id) {
        Response response = new Response();

        try {
            // Retrieve the damaged product from the repository
            ProductDamage productDamage = productDamageRepository.findById(id)
                    .orElseThrow(() -> new MyException("Product Damage not found for the given ID: " + id));

            // Map the entity to DTO
            ProductDamageDTO productDamageDTO = Mapper.mapProductDamageEntityToProductDamageDTo(productDamage);

            // Set response object
            response.setMessage("Product Damage retrieved successfully");
            response.setStatusCode(200);
            response.setData("ProductDamage", productDamageDTO);

        } catch (MyException e) {
            // Handle custom exceptions
            response.setStatusCode(404);
            response.setMessage("Unable to retrieve Damaged Product: " + e.getMessage());

        } catch (Exception e) {
            // Handle general exceptions
            response.setStatusCode(500);
            response.setMessage("An error occurred: " + e.getMessage());
        }

        return response;
    }


    @Override
    public Response getAllDamagedProduct() {
        Response response = new Response();

        try {
            // Retrieve all damaged products from the repository
            List<ProductDamage> damagedProducts = productDamageRepository.findAll();

            // Map each entity to DTOs
            List<ProductDamageDTO> productDamageDTOs = damagedProducts.stream()
                    .map(Mapper::mapProductDamageEntityToProductDamageDTo)
                    .collect(Collectors.toList());

            // Set response object
            response.setMessage("All Damaged Products retrieved successfully");
            response.setStatusCode(200);
            response.setData("DamagedProducts", productDamageDTOs);

        } catch (Exception e) {
            // Handle general exceptions
            response.setStatusCode(500);
            response.setMessage("An error occurred: " + e.getMessage());
        }

        return response;

    }

}
