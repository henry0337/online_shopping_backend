package com.henry.online_shopping.service;

import com.henry.online_shopping.entity.Category;
import com.henry.online_shopping.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public List<Category> getAll() {
        return repository.findAll();
    }

    public boolean existsById(int id) {
        return repository.existsById(id);
    }

    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    public Category getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Category insert(Category category) {
        return repository.save(category);
    }

    public Category update(int id, Category category) {
        Optional<Category> currentCategory = repository.findById(id);
        if (currentCategory.isPresent()) {
            Category newCategory = currentCategory.get();

            newCategory.setName(category.getName());
            newCategory.setImage(category.getImage());
            return repository.save(newCategory);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
