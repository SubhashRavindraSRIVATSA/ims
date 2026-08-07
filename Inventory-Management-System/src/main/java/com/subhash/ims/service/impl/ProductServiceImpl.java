package com.subhash.ims.service.impl;

import com.subhash.ims.dto.ProductDTO;
import com.subhash.ims.entity.Category;
import com.subhash.ims.entity.Product;
import com.subhash.ims.mapper.ProductMapper;
import com.subhash.ims.repository.CategoryRepository;
import com.subhash.ims.repository.ProductRepository;
import com.subhash.ims.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/product-image/";

    //AFTER YOUR FROTEND IS SET UP WROTE THIS SO THE IMAGE IS SAVED IN YOUR FRONTEND PUBLIC FOLDER
    private static final String IMAGE_DIRECTOR_FRONTEND = "/Users/dennismac/phegonDev/ims-angular/public/products/";

    @Override
    // =========================
    // CREATE
    // =========================

    public ProductDTO createProduct(ProductDTO dto) {

        Product product = productMapper.toEntity(dto);

        // Handle category
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));

            product.setCategory(category);
        }

        Product saved = productRepository.save(product);
        return productMapper.toDTO(saved);
    }

//    private String saveImageToFrontendPublicFolder(MultipartFile imageFile) {
//        //validate image check
//        if (!imageFile.getContentType().startsWith("image/")){
//            throw new IllegalArgumentException("Only image files are allowed");
//        }
//        //create the directory to store images if it doesn't exist
//        File directory = new File(IMAGE_DIRECTOR_FRONTEND);
//
//        if (!directory.exists()){
//            directory.mkdir();
//            log.info("Directory was created");
//        }
//        //generate unique file name for the image
//        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
//        //get the absolute path of the image
//        String imagePath = IMAGE_DIRECTOR_FRONTEND + uniqueFileName;
//
//        try {
//            File desctinationFile = new File(imagePath);
//            imageFile.transferTo(desctinationFile); //we are transfering(writing to this folder)
//
//        }catch (Exception e){
//            throw new IllegalArgumentException("Error occurend while saving image" + e.getMessage());
//        }
//
//        return "products/"+uniqueFileName;
//    }

//    @Override
//    public Response updateProduct(ProductDTO productDTO, MultipartFile imageFile) {
//        Product existingProduct = productRepository.findById(productDTO.getProductId())
//                .orElseThrow(()-> new NotFoundException("Product Not Found"));
//
//        //check if image is associated with the update request
//        if (imageFile != null && !imageFile.isEmpty()){
//            String imagePath = saveImageToFrontendPublicFolder(imageFile);
//            existingProduct.setImageUrl(imagePath);
//        }
//        //Check if category is to be changed for the product
//        if (productDTO.getCategoryId() != null && productDTO.getCategoryId() > 0){
//
//            Category category = categoryRepository.findById(productDTO.getCategoryId())
//                    .orElseThrow(()-> new NotFoundException("Category Not Found"));
//            existingProduct.setCategory(category);
//        }
//
//        //check and update fiedls
//
//        if (productDTO.getName() !=null && !productDTO.getName().isBlank()){
//            existingProduct.setName(productDTO.getName());
//        }
//
//        if (productDTO.getSku() !=null && !productDTO.getSku().isBlank()){
//            existingProduct.setSku(productDTO.getSku());
//        }
//
//        if (productDTO.getDescription() !=null && !productDTO.getDescription().isBlank()){
//            existingProduct.setDescription(productDTO.getDescription());
//        }
//
//        if (productDTO.getPrice() !=null && productDTO.getPrice().compareTo(BigDecimal.ZERO) >=0){
//            existingProduct.setPrice(productDTO.getPrice());
//        }
//
//        if (productDTO.getStockQuantity() !=null && productDTO.getStockQuantity() >=0){
//            existingProduct.setStockQuantity(productDTO.getStockQuantity());
//        }
//
//        //Update the product
//        productRepository.save(existingProduct);
//        return Response.builder()
//                .status(200)
//                .message("Product successfully Updated")
//                .build();
//
//    }

//    @Override
//    public Response getAllProducts() {
//        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
//
//        List<ProductDTO> productDTOS = modelMapper.map(products, new TypeToken<List<ProductDTO>>() {}.getType());
//
//        return Response.builder()
//                .status(200)
//                .message("success")
//                .products(productDTOS)
//                .build();
//    }

    // =========================
    // GET
    // =========================

    @Transactional
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Transactional
    public List<ProductDTO> getAllProducts() {
        return productMapper.toDTOList(productRepository.findAll());
    }

//    @Override
//    public Response deleteProduct(Long id) {
//        productRepository.findById(id)
//                .orElseThrow(()-> new NotFoundException("Product Not Found"));
//
//        productRepository.deleteById(id);
//
//        return Response.builder()
//                .status(200)
//                .message("Product successfully deleted")
//                .build();
//    }

    // =========================
    // UPDATE
    // =========================
    @Override
    public ProductDTO updateProduct(Long id, ProductDTO dto) {

        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Update fields manually (controlled update)
        existing.setName(dto.getName());
        existing.setPrice(dto.getPrice());
        existing.setStockQuantity(dto.getStockQuantity());
        existing.setUnit(dto.getUnit());
        existing.setDescription(dto.getDescription());
        existing.setExpiryDate(dto.getExpiryDate());

        // Category update
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            existing.setCategory(category);
        }

        return productMapper.toDTO(productRepository.save(existing));
    }

//    private String saveImage(MultipartFile imageFile){
//        //validate image check
//        if (!imageFile.getContentType().startsWith("image/")){
//            throw new IllegalArgumentException("Only image files are allowed");
//        }
//        //create the directory to store images if it doesn't exist
//        File directory = new File(IMAGE_DIRECTORY);
//
//        if (!directory.exists()){
//            directory.mkdir();
//            log.info("Directory was created");
//        }
//        //generate unique file name for the image
//        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
//        //get the absolute path of the image
//        String imagePath = IMAGE_DIRECTORY + uniqueFileName;
//
//        try {
//            File desctinationFile = new File(imagePath);
//            imageFile.transferTo(desctinationFile); //we are transfering(writing to this folder)
//
//        }catch (Exception e){
//            throw new IllegalArgumentException("Error occurend while saving image" + e.getMessage());
//        }
//
//        return imagePath;
//    }

    // =========================
    // DELETE
    // =========================
    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }

    // =========================
    // INTERNAL
    // =========================
    @Override
    public Product getEntity(Long id) {
        return null;
    }
}
