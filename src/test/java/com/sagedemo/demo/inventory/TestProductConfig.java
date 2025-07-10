package com.sagedemo.demo.inventory;

import com.sagedemo.demo.inventory.entity.Product;
import com.sagedemo.demo.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestProductConfig {
    @Autowired
    private ProductRepository productRepository;

    @Bean
    public Product testProduct() {
        Product product = new Product();
        product.setSku("TEST-SKU");
        product.setName("Test Product");
        product.setDescription("Test product for integration tests");
        product.setPrice(java.math.BigDecimal.valueOf(1.0));
        product.setUnit("pcs");
        return productRepository.save(product);
    }
}
