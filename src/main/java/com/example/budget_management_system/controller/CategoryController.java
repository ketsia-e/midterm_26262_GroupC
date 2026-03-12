package com.example.budget_management_system.controller;

import com.example.budget_management_system.entity.Category;
import com.example.budget_management_system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Map<String, String> request) {
        String categoryName = request.get("categoryName");
        String description = request.get("description");
        Category category = categoryService.createCategory(categoryName, description);
        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        
        if (page == 0 && size == 10) {
            return ResponseEntity.ok(categoryService.getAllCategories());
        }
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Category> categoryPage = categoryService.getAllCategories(pageable);
        return ResponseEntity.ok(categoryPage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }
}
