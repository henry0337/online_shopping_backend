package com.henry.online_shopping.controller;

import com.henry.online_shopping.dto.request.ProductRequest;
import com.henry.online_shopping.entity.Product;
import com.henry.online_shopping.mapper.ProductMapper;
import com.henry.online_shopping.service.ProductService;
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
@RequestMapping("/api/v1/product")
@Tag(name = "Product")
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        final var body = service.getAll();
        if (body.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable int id) {
        final var body = service.getById(id);
        return service.existsById(id) ? ResponseEntity.ok(body) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product insert(@NonNull @RequestBody ProductRequest request) {
        Product product = mapper.requestToModel(request);
        return service.insert(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable int id, @RequestBody ProductRequest request) {
        if (!service.existsById(id)) return ResponseEntity.notFound().build();
        Product product = mapper.requestToModel(request);
        return ResponseEntity.ok(service.update(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!service.existsById(id)) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
