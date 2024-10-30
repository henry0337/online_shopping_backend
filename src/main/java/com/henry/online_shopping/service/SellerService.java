package com.henry.online_shopping.service;

import com.henry.online_shopping.entity.Seller;
import com.henry.online_shopping.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository repository;

    public List<Seller> getAll() {
        return repository.findAll();
    }

    public boolean existsById(int id) {
        return repository.existsById(id);
    }

    public Seller getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Seller insert(Seller seller) {
        return repository.save(seller);
    }

    public Seller update(int id, Seller seller) {
        Optional<Seller> currentSeller = repository.findById(id);
        if (currentSeller.isPresent()) {
            Seller newSeller = currentSeller.get();

            newSeller.setName(seller.getName());
            newSeller.setAvatar(seller.getAvatar());
            newSeller.setTel(seller.getTel());
            return repository.save(newSeller);
        } else {
            return null;
        }
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
