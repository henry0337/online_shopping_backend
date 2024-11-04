package com.henry.online_shopping.service;

import com.henry.online_shopping.entity.Product;
import com.henry.online_shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<Product> getAll() {
        return repository.findAll();
    }

    public boolean existsById(int id) {
        return repository.existsById(id);
    }

    public Product getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Product insert(Product product) {
        return repository.save(product);
    }

    public Product update(int id, Product product) {
        Optional<Product> currentProduct = repository.findById(id);
        if (currentProduct.isPresent()) {
            Product newProduct = currentProduct.get();

            newProduct.setName(product.getName());
            newProduct.setPrice(product.getPrice());
            newProduct.setRating(product.getRating());
            newProduct.setImages(product.getImages());
            newProduct.setSize(product.getSize());
            newProduct.setCategory(product.getCategory());
            newProduct.setSeller(product.getSeller());
            return repository.save(newProduct);
        } else {
            return null;
        }
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
