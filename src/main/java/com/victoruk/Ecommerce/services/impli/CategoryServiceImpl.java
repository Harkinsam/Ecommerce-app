package com.victoruk.Ecommerce.services.impli;

import com.victoruk.Ecommerce.Exception.MyException;
import com.victoruk.Ecommerce.Mapper.Mapper;
import com.victoruk.Ecommerce.dto.CategoryDTO;
import com.victoruk.Ecommerce.dto.ProductDTO;
import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.Category;
import com.victoruk.Ecommerce.entity.Product;
import com.victoruk.Ecommerce.repository.CategoryRepository;
import com.victoruk.Ecommerce.repository.ProductRepository;
import com.victoruk.Ecommerce.services.interfac.CategoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryInterface {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Response addCategory(Category category) {
        Response response = new Response();

        try {
            Category savedCategory = categoryRepository.save(category);
            CategoryDTO addedcategory = Mapper.mapCategoryEntityToCategoryDto(category);


            response.setStatusCode(201);
            response.setMessage("Category added successfully.");
            response.setData(Map.of("category",addedcategory));
            response.setData("category",addedcategory);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error adding category: " + e.getMessage());
        }

        return response;
    }


    @Override
    public Response updateCategory(Long categoryId, Category category) {
        Response response = new Response();

        try {
            // Retrieve the existing category from the database
            Category savedCategory = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new MyException("Category not found: " + categoryId));

            // Update category details if they are non-null in the input category object
            if (category.getName() != null) {
                savedCategory.setName(category.getName());
            }

            // Save the updated category back to the database
            Category updatedCategory = categoryRepository.save(savedCategory);

            // Map the updated entity to DTO
            CategoryDTO categoryDTO = Mapper.mapCategoryEntityToCategoryDto(updatedCategory);

            // Build the response object
            response.setStatusCode(200);
            response.setMessage("Category updated successfully.");
            response.setData(Map.of("category", categoryDTO));

        } catch (MyException e) {
            // Handle the case where the category was not found
            response.setStatusCode(400);
            response.setMessage("Update failed: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other unexpected errors
            response.setStatusCode(500);
            response.setMessage("Error updating category: " + e.getMessage());
        }

        return response;
    }


    @Override
    public Response deleteCategory(Long categoryId) {
        Response response = new Response();

        try {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new MyException("Category not found: " + categoryId));

            categoryRepository.delete(category);

            response.setStatusCode(200);
            response.setMessage("Category deleted successfully.");

        } catch (MyException e) {
            response.setStatusCode(400);
            response.setMessage("Unable to delete category: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting category: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getCategoryById(Long categoryId) {
        Response response = new Response();

        try {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new MyException("Category not found: " + categoryId));
            CategoryDTO categoryDTO = Mapper.mapCategoryEntityToCategoryDto(category);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setData(Map.of("category", categoryDTO));

        } catch (MyException e) {
            response.setStatusCode(400);
            response.setMessage("Unable to get category: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching category: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllCategories() {
        Response response = new Response();

        try {
            List<Category> categories = categoryRepository.findAll();
            List<CategoryDTO> categoryDTOList = categories.stream()
                    .map(Mapper::mapCategoryEntityToCategoryDto)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setData(Map.of("categories", categoryDTOList));

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching categories: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response searchCategoryByName(String name) {
        Response response = new Response();

        try {
            List<Category> categories = categoryRepository.findByNameContainingIgnoreCase(name);
            List<CategoryDTO> categoryDTOList = categories.stream()
                    .map(Mapper::mapCategoryEntityToCategoryDto)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setData(Map.of("categories", categoryDTOList));

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error searching categories: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getProductsByCategory(Long categoryId) {
        Response response = new Response();

        try {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new MyException("Category not found: " + categoryId));

            List<ProductDTO> productDTOList = category.getProducts().stream()
                    .map(Mapper::mapProductEntityToProductDto)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setData(Map.of("products", productDTOList));

        } catch (MyException e) {
            response.setStatusCode(400);
            response.setMessage("Unable to get products for category: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching products by category: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response addProductToCategory(Long categoryId, Long productId) {
        Response response = new Response();

        try {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new MyException("Category not found: " + categoryId));

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new MyException("Product not found: " + productId));

            product.setCategory(category);
            productRepository.save(product);

            response.setStatusCode(200);
            response.setMessage("Product added to category successfully.");
            response.setData(Map.of("product", Mapper.mapProductEntityToProductDto(product)));

        } catch (MyException e) {
            response.setStatusCode(400);
            response.setMessage("Unable to add product to category: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error adding product to category: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response removeProductFromCategory(Long categoryId, Long productId) {
        Response response = new Response();

        try {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new MyException("Category not found: " + categoryId));

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new MyException("Product not found: " + productId));

            product.setCategory(null);
            productRepository.save(product);

            response.setStatusCode(200);
            response.setMessage("Product removed from category successfully.");
            response.setData(Map.of("product", Mapper.mapProductEntityToProductDto(product)));

        } catch (MyException e) {
            response.setStatusCode(400);
            response.setMessage("Unable to remove product from category: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error removing product from category: " + e.getMessage());
        }

        return response;
    }
}
