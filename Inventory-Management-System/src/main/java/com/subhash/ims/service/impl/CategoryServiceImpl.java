package com.subhash.ims.service.impl;

import com.subhash.ims.dto.CategoryDTO;
import com.subhash.ims.dto.Response;
import com.subhash.ims.entity.Category;
import com.subhash.ims.exceptions.NotFoundException;
import com.subhash.ims.mapper.CategoryMapper;
import com.subhash.ims.repository.CategoryRepository;
import com.subhash.ims.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // =========================
    // CREATE
    // =========================
    @Override
    public CategoryDTO createCategory(CategoryDTO dto) {
        Category category = categoryMapper.toEntity(dto);

        // Handle parent
        if (dto.getParentId() != null) {
            Category parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent category not found"));

            category.setParentCategory(parent);
        }

        Category saved = categoryRepository.save(category);
        return categoryMapper.toDTO(saved);
    }

    // =========================
    // GET
    // =========================
    @Override
    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryMapper.toDTOList(categoryRepository.findAll());
    }

    // =========================
    // UPDATE
    // =========================
    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        existing.setName(dto.getName());

        // Handle parent safely
        if (dto.getParentId() != null) {

            if (dto.getParentId().equals(id)) {
                throw new IllegalArgumentException("Category cannot be its own parent");
            }

            Category parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent category not found"));

            // 🔥 Prevent cycles
            if (isCyclic(existing, parent)) {
                throw new IllegalArgumentException("Cyclic category hierarchy detected");
            }

            existing.setParentCategory(parent);
        } else {
            existing.setParentCategory(null);
        }

        return categoryMapper.toDTO(categoryRepository.save(existing));
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        // Optional safety: prevent delete if products exist
        if (!category.getProducts().isEmpty()) {
            throw new IllegalStateException("Cannot delete category with products");
        }

        categoryRepository.delete(category);
    }


    // =========================
    // INTERNAL: CYCLE CHECK
    // =========================

    private boolean isCyclic(Category child, Category parent) {

        Category current = parent;

        while (current != null) {
            if (current.getCategoryId().equals(child.getCategoryId())) {
                return true;
            }
            current = current.getParentCategory();
        }

        return false;
    }

//    @Override
//    public Response createCategory(CategoryDTO categoryDTO) {
//        Category categoryToSave = modelMapper.map(categoryDTO, Category.class);
//        categoryRepository.save(categoryToSave);
//
//        return Response.builder()
//                .status(200)
//                .message("Category created successfully")
//                .build();
//    }
//
//    @Override
//    public Response getAllCategories() {
//        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
//
//        List<CategoryDTO> categoryDTOS = modelMapper.map(categories, new TypeToken<List<CategoryDTO>>() {}.getType());
//
//        return Response.builder()
//                .status(200)
//                .message("success")
//                .categories(categoryDTOS)
//                .build();
//    }
//
//    @Override
//    public Response getCategoryById(Long id) {
//        Category category = categoryRepository.findById(id)
//                .orElseThrow(()-> new NotFoundException("Category Not Found"));
//        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
//
//        return Response.builder()
//                .status(200)
//                .message("success")
//                .category(categoryDTO)
//                .build();
//    }
//
//    @Override
//    public Response updateCategory(Long id, CategoryDTO categoryDTO) {
//        Category existingCategory = categoryRepository.findById(id)
//                .orElseThrow(()-> new NotFoundException("Category Not Found"));
//
//        existingCategory.setName(categoryDTO.getName());
//        categoryRepository.save(existingCategory);
//
//        return Response.builder()
//                .status(200)
//                .message("Category Successfully Updated")
//                .build();
//    }
//
//    @Override
//    public Response deleteCategory(Long id) {
//        categoryRepository.findById(id)
//                .orElseThrow(()-> new NotFoundException("Category Not Found"));
//
//        categoryRepository.deleteById(id);
//
//        return Response.builder()
//                .status(200)
//                .message("Category Successfully Deleted")
//                .build();
//    }
}
