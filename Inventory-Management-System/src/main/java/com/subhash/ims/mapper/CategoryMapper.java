package com.subhash.ims.mapper;

import com.subhash.ims.dto.CategoryDTO;
import com.subhash.ims.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "categoryId", target = "id")
    @Mapping(source = "parentCategory.categoryId", target = "parentId")
    @Mapping(source = "parentCategory.name", target = "parentName")
    @Mapping(target = "subCategoryIds",
            expression = "java(category.getSubCategories().stream()"
                    + ".map(Category::getCategoryId)"
                    + ".collect(java.util.stream.Collectors.toList()))")
    @Mapping(target = "productIds",
            expression = "java(category.getProducts().stream()"
                    + ".map(Product::getId)"
                    + ".collect(java.util.stream.Collectors.toList()))")
    List<CategoryDTO> toDTOList(List<Category> categories);
    @Mapping(target = "parentCategory", ignore = true)
    @Mapping(target = "subCategories", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toEntity(CategoryDTO dto);
    CategoryDTO toDTO(Category category);
}