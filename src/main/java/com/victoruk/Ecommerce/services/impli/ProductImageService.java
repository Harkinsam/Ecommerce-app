package com.victoruk.Ecommerce.services.impli;

import com.victoruk.Ecommerce.Exception.MyException;
import com.victoruk.Ecommerce.Mapper.Mapper;
import com.victoruk.Ecommerce.cloudService.CloudinaryService;
import com.victoruk.Ecommerce.dto.ProductImageDTO;
import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.Product;
import com.victoruk.Ecommerce.entity.ProductImage;
import com.victoruk.Ecommerce.repository.ProductImageRepository;
import com.victoruk.Ecommerce.repository.ProductRepository;
import com.victoruk.Ecommerce.services.interfac.ProductImageInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductImageService implements ProductImageInterface {

    private final ProductImageRepository productImageRepository;
    private final CloudinaryService cloudinaryService;
    private final ProductRepository productRepository;


    public ProductImageService(ProductImageRepository productImageRepository, CloudinaryService cloudinaryService, ProductRepository productRepository) {
        this.productImageRepository = productImageRepository;
        this.cloudinaryService = cloudinaryService;
        this.productRepository = productRepository;
    }
//////
    public Response createProductImage(Long productId, String altText, MultipartFile photo) {
        Response response = new Response();
        try {
            // Fetch the product to which the image will be associated
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Upload the provided photo to Cloudinary and retrieve the result
            Map<String, Object> uploadResult = cloudinaryService.upload(photo);

            // Extract the URL of the uploaded photo from the result map
            String photoUrl = (String) uploadResult.get("url");

            ProductImage productImage = new ProductImage();
            productImage.setImageUrl(photoUrl);
            productImage.setAltText(altText);
            productImage.setProduct(product); // Associate the image with the product

            // Save the ProductImage entity in the database
            ProductImage savedProductImage = productImageRepository.save(productImage);

            // Map to DTO and prepare response
            ProductImageDTO productImageDTO = Mapper.mapProductImageentityToDto(savedProductImage);
            response.setMessage("Product image created successfully");
            response.setStatusCode(201);
            response.setData("productImage", productImageDTO);

        } catch (MyException e) {
            response.setStatusCode(400);
            response.setMessage("Error creating product image/ invalid credential: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error creating product image: please try again: " + e.getMessage());
        }
        return response;
    }


//    @Override
//    public Response createProductImage(String altText, MultipartFile photo) {
//        Response response = new Response();
//        try {
//
//            // Upload the provided photo to Cloudinary and retrieve the result
//            Map<String, Object> uploadResult = cloudinaryService.upload(photo);
//
//            // Extract the URL of the uploaded photo from the result map
//            String photoUrl = (String) uploadResult.get("url");
//
//            ProductImage productImage = new ProductImage();
//
//            productImage.setImageUrl(photoUrl);
//            productImage.setAltText(altText);
//
//
//            // Save the ProductImage entity in the database
//            ProductImage savedProductImage = productImageRepository.save(productImage);
//
//            // Map to DTO and prepare response
//            ProductImageDTO productImageDTO = Mapper.mapProductImageentityToDto(savedProductImage);
//            response.setMessage("Product image created successfully");
//            response.setStatusCode(201);
//            response.setData("ProductImage", productImageDTO);
//
//        } catch (MyException e) {
//            response.setStatusCode(400);
//            response.setMessage("Error creating product image/ invalid credential: " + e.getMessage());
//        } catch (Exception e) {
//            response.setStatusCode(500);
//            response.setMessage("Error creating product image: please try again: " + e.getMessage());
//        }
//        return response;
//    }


    @Override
    public Response updateProductImage(Long id, MultipartFile newImageFile, String altText) {
        Response response = new Response();
        try {
            Optional<ProductImage> existingProductImageOptional = productImageRepository.findById(id);
            if (existingProductImageOptional.isPresent()) {
                ProductImage existingProductImage = existingProductImageOptional.get();

                // Upload the new image to Cloudinary
                Map<String, Object> uploadResult = cloudinaryService.upload(newImageFile);
                String newImageUrl = (String) uploadResult.get("url");

                // Update image details in the existing product image record
                existingProductImage.setImageUrl(newImageUrl);
                existingProductImage.setAltText(altText);

                ProductImage updatedProductImage = productImageRepository.save(existingProductImage);
                ProductImageDTO productImageDTO = Mapper.mapProductImageentityToDto(updatedProductImage);

                response.setMessage("Product image updated successfully");
                response.setStatusCode(201);
                response.setData("updated Image", productImageDTO);
            } else {
                throw new MyException("Product image not found for ID: " + id);
            }
        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage("Unable to update image. Check your credentials: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating product image: " + e.getMessage());
        }
        return response;
    }



    @Override
    public Response deleteProductImage(Long id) {
        Response response = new Response();
        try {
            if (productImageRepository.existsById(id)) {
                productImageRepository.deleteById(id);
                response.setMessage("Product image deleted successfully");
                response.setStatusCode(200);
            } else {
                throw new MyException("Product image not found for ID: " + id);
            }
        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting product image: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getProductImageById(Long id) {
        Response response = new Response();
        try {
            ProductImage productImage = productImageRepository.findById(id)
                    .orElseThrow(() -> new MyException("Product image not found for ID: " + id));
            response.setMessage("Product image retrieved successfully");
            response.setStatusCode(200);
            response.setData("ProductImage", productImage);
        } catch (MyException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving product image: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllProductImage() {
        Response response = new Response();
        try {
            List<ProductImage> productImages = productImageRepository.findAll();

            // Convert List<ProductImage> to List<ProductImageDTO>
            List<ProductImageDTO> productImageDTOS = productImages
                    .stream()
                    .map(Mapper::mapProductImageentityToDto)
                    .collect(Collectors.toList());

            response.setMessage("All product images retrieved successfully");
            response.setStatusCode(200);
            response.setData("ProductImages", productImageDTOS);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving product images: " + e.getMessage());
        }
        return response;
    }

}
