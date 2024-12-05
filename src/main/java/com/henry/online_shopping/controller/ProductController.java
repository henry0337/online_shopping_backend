package com.henry.online_shopping.controller;

import com.henry.online_shopping.annotation.FasterRestController;
import com.henry.online_shopping.annotation.OneLineMapping;
import com.henry.online_shopping.dto.request.ProductRequest;
import com.henry.online_shopping.entity.Product;
import com.henry.online_shopping.mapper.ProductMapper;
import com.henry.online_shopping.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FasterRestController(url = "/api/v1/product", name = "Product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService service;
    ProductMapper mapper;

    @GetMapping
    public ResponseEntity<?> getAllOrById(@RequestParam(required = false) String id) {
        if (id != null) {
            final int idAsInt = Integer.parseInt(id);
            final var body = service.getById(idAsInt);
            return service.existsById(idAsInt) ? ResponseEntity.ok(body) : ResponseEntity.notFound().build();
        } else {
            final var body = service.getAll();
            if (body.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(body);
        }
    }

    @OneLineMapping(method = RequestMethod.POST, status = HttpStatus.CREATED)
    public Product insert(@RequestBody ProductRequest request) {
        Product product = mapper.requestToModel(request);
        return service.insert(product);
    }

    @PutMapping
    public ResponseEntity<Product> update(@RequestParam int id, @RequestBody ProductRequest request) {
        if (!service.existsById(id)) return ResponseEntity.notFound().build();
        Product product = mapper.requestToModel(request);
        return ResponseEntity.ok(service.update(id, product));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id) {
        if (!service.existsById(id)) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
