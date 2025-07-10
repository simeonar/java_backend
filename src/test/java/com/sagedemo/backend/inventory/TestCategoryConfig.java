package com.sagedemo.backend.inventory;

import com.sagedemo.backend.inventory.entity.Category;
import com.sagedemo.backend.inventory.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestCategoryConfig {
    @Autowired
    private CategoryRepository categoryRepository;

    @Bean
    public Category testCategory() {
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test category for integration tests");
        return categoryRepository.save(category);
    }
}
