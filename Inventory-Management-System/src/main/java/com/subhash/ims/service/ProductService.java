package com.subhash.ims.service;

import com.subhash.ims.dto.ProductDTO;
import com.subhash.ims.dto.Response;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    Response saveProduct(ProductDTO productDTO, MultipartFile imageFile);
    Response updateProduct(ProductDTO productDTO, MultipartFile imageFile);
    Response getAllProducts();
    Response getProductById(Long id);
    Response deleteProduct(Long id);
}
