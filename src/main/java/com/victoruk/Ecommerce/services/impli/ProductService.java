package com.victoruk.Ecommerce.services.impli;

import com.victoruk.Ecommerce.Exception.MyException;
import com.victoruk.Ecommerce.dto.ProductDTO;
import com.victoruk.Ecommerce.dto.ProductImageDTO;
import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.Product;
import com.victoruk.Ecommerce.entity.ProductImage;
import com.victoruk.Ecommerce.repository.ProductImageRepository;
import com.victoruk.Ecommerce.repository.ProductRepository;
import com.victoruk.Ecommerce.services.interfac.ProductInterface;
import com.victoruk.Ecommerce.Mapper.Mapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.victoruk.Ecommerce.Mapper.Mapper.mapProductEntityToProductDto;

@Service
public class ProductService implements ProductInterface {

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;



    public ProductService(ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;

    }



    @Override
    public Response addProduct(Product product) {
        Response response = new Response();

        try {
            // Save the product to the repository
            Product savedProduct = productRepository.save(product);
            var productDTO = mapProductEntityToProductDto(savedProduct);

            response.setStatusCode(201);
            response.setMessage("Product added successfully.");
            response.setData(Map.of("product", productDTO));

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("An unexpected error occurred: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response updateProduct(Long id, String name, String description,
                                  BigDecimal price,
                                  LocalDate expiryDate) {
        Response response = new Response();

        try {
            // Check if the product exists
            var existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new MyException("Product not found: " + id));

            // Update the product details
            if (name != null) existingProduct.setName(name);
            if (description != null) existingProduct.setDescription(description);
            if (price != null) existingProduct.setPrice(price);
            if (expiryDate != null) existingProduct.setExpiryDate(expiryDate);
            productRepository.save(existingProduct);

            response.setStatusCode(200);
             response.setMessage("Product updated successfully.");
            response.setData(Map.of("product", mapProductEntityToProductDto(existingProduct)));

        } catch (MyException e) {
            response.setStatusCode(400);
            response.setMessage("Update failed: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("An unexpected error occurred: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllProduct() {
        Response response = new Response();

        try {
            List<Product> productList = productRepository.findAll();

            List<ProductDTO> productDTOList = productList.stream().map(product -> {
                // Map product to ProductDTO
                ProductDTO productDTO = Mapper.mapProductEntityToProductDto(product);

                // Check for discount and set discount ID if applicable
                if (product.getDiscount() != null) {
                    productDTO.setDiscountId(product.getDiscount().getId());
                }

                // Map associated ProductImages to ProductImageDTOs and set them in ProductDTO
                List<ProductImageDTO> productImageDTOs = product.getProductImages().stream()
                        .map(Mapper::mapProductImageentityToDto)
                        .collect(Collectors.toList());
                productDTO.setProductImages(productImageDTOs);

                return productDTO;
            }).collect(Collectors.toList());

            response.setMessage("Successful");
            response.setStatusCode(200);
            response.setData("products", productDTOList);

        } catch (Exception e) {
            response.setMessage("Error fetching products: " + e.getMessage());
            response.setStatusCode(500);
        }

        return response;
    }

//    @Override
//    public Response getAllProduct() {
//        Response response = new Response();
//
//        try {
//            List<Product> productList = productRepository.findAll();
//
//            List<ProductDTO> productDTOList = productList.stream().map(product -> {
//                // Map product to ProductDTO
//                ProductDTO productDTO = Mapper.mapProductEntityToProductDto(product);
//
//                product.getDiscount().getId();
//                // Map associated ProductImages to ProductImageDTOs and set them in ProductDTO
//                List<ProductImageDTO> productImageDTOs = product.getProductImages().stream()
//                        .map(Mapper::mapProductImageentityToDto)
//                        .collect(Collectors.toList());
//                productDTO.setProductImages(productImageDTOs);
//
//                return productDTO;
//            }).collect(Collectors.toList());
//
//            response.setMessage("Successful");
//            response.setStatusCode(200);
//            response.setData("products", productDTOList);
//
//        } catch (Exception e) {
//            response.setMessage("Error fetching products: " + e.getMessage());
//            response.setStatusCode(500);
//        }
//
//        return response;
//    }

    @Override
    public Response getProductById(Long productId) {
        Response response = new Response();
        try {
            // Fetch the product by ID
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Map the product to ProductDTO
            ProductDTO productDTO = mapProductEntityToProductDto(product);

            // Fetch and add the associated product images
            // Fetch the list of product images associated with this product
            List<ProductImage> productImages = productImageRepository.findByProductId(productId);

            // Map the list of ProductImage entities to a list of ProductImageDTOs
            // This is where we map each ProductImage to a ProductImageDTO
            List<ProductImageDTO> productImageDTOs = productImages.stream()
                    .map(image -> Mapper.mapProductImageentityToDto(image))
                    .collect(Collectors.toList());  // Collect the results into a List


            // Set the images in the productDTO
            // Add the list of product image DTOs to the productDTO
            productDTO.setProductImages(productImageDTOs);

            // Set the response data (productDTO now includes product images)
            response.setStatusCode(200);
            response.setMessage("Product retrieved successfully");
            response.setData("product", productDTO);  // Set the productDTO with images in response

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching product: " + e.getMessage());
        }
        return response;
    }


    // this method is to get check expiring date of product that are less or equal six months
    @Override
    public Response getProductByExpiringDate() {
        Response response = new Response();

        try {
            LocalDate sixMonthsFromNow = LocalDate.now().plusMonths(6);
            List<Product> productList = productRepository.findByExpiryDateLessThanEqual(sixMonthsFromNow);

            List<ProductDTO> productDTOList = productList.stream().map(Mapper::mapProductEntityToProductDto)
                    .collect(Collectors.toList());

            response.setMessage("Successful");
            response.setStatusCode(200);
            response.setData("expiringProducts", productDTOList);

        } catch (Exception e) {
            response.setMessage("Error fetching expiring products: " + e.getMessage());
            response.setStatusCode(500);
        }

        return response;
    }

    @Override
    public Response deleteProduct(Long productId) {
        Response response = new Response();

        try {
            var product = productRepository.findById(productId)
                    .orElseThrow(() -> new MyException("Product ID not found: " + productId));

            productRepository.delete(product);

            response.setStatusCode(200);
            response.setMessage("Product deleted successfully.");

        } catch (MyException e) {
            response.setStatusCode(400);
            response.setMessage("Unable to delete product: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("An error occurred: " + e.getMessage());
        }

        return response;
    }
}
