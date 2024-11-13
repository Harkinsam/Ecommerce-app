package com.victoruk.Ecommerce.services.interfac;

import com.victoruk.Ecommerce.dto.Response;
import com.victoruk.Ecommerce.entity.Category;

public interface CategoryInterface {



    Response addCategory(Category category);


    Response updateCategory(Long categoryId, Category category);

    Response deleteCategory(Long categoryId);
    Response getCategoryById(Long categoryId);
    Response getAllCategories();
    Response searchCategoryByName(String name);


    // category/product methods
    Response getProductsByCategory(Long categoryId);
    Response addProductToCategory(Long categoryId, Long productId);
    Response removeProductFromCategory(Long categoryId, Long productId);


}
