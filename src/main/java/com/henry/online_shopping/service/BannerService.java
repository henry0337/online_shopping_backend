package com.henry.online_shopping.service;

import com.henry.online_shopping.entity.Banner;
import com.henry.online_shopping.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository repository;

    public List<Banner> getAll() {
        return repository.findAll();
    }

    public boolean existsById(int id) {
        return repository.existsById(id);
    }

    public boolean existsByUri(String uri) {
        return repository.existsByUrl(uri);
    }

    public Banner getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Banner insert(Banner banner) {
        return repository.save(banner);
    }

    public Banner update(int id, Banner banner) {
        Optional<Banner> currentBanner = repository.findById(id);
        if (currentBanner.isPresent()) {
            Banner newBanner = currentBanner.get();

            newBanner.setUrl(banner.getUrl());
            return repository.save(newBanner);
        } else {
            return null;
        }
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
