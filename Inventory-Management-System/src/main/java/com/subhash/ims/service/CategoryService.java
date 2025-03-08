package com.subhash.ims.service;

import com.subhash.ims.dto.CategoryDTO;
import com.subhash.ims.dto.Response;

public interface CategoryService {
    Response createCategory(CategoryDTO categoryDTO);
    Response getAllCategories();
    Response getCategoryById(Long id);
    Response updateCategory(Long id, CategoryDTO categoryDTO);
    Response deleteCategory(Long id);
}
