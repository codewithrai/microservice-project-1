package com.programmingtechie.service;

import com.programmingtechie.dto.ProductDTO;
import com.programmingtechie.entity.Product;
import com.programmingtechie.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        productRepository.save(product);

        log.info("Product {} is saved", product.getId());
    }

    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();

        List<ProductDTO> collect = products.stream().map(item -> {
            ProductDTO product = new ProductDTO();
            product.setId(item.getId());
            product.setName(item.getName());
            product.setDescription(item.getDescription());
            product.setPrice(item.getPrice());
            return product;
        }).toList();
        return collect;
    }

}
