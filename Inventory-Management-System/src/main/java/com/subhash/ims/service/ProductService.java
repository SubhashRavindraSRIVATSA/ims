package com.subhash.ims.service;

import com.subhash.ims.dto.ProductDTO;
import com.subhash.ims.dto.Response;
import com.subhash.ims.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    ProductDTO updateProduct(Long id, ProductDTO dto);
    void deleteProduct(Long id);
    Product getEntity(Long id);
}
