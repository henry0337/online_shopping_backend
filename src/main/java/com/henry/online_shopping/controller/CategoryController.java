package com.henry.online_shopping.controller;

import com.henry.online_shopping.entity.Category;
import com.henry.online_shopping.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@Tag(name = "Category")
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        final var body = service.getAll();
        if (body.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable int id) {
        final var body = service.getById(id);
        return service.existsById(id) ? ResponseEntity.ok(body) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Category> insert(@NonNull @RequestBody Category category) {
        if (service.existsByName(category.getName())) return ResponseEntity.badRequest().build();
        return new ResponseEntity<>(service.insert(category), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable int id, @RequestBody Category category) {
        if (!service.existsById(id)) return ResponseEntity.notFound().build();
        if (service.existsByName(category.getName())) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(service.update(id, category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!service.existsById(id)) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
