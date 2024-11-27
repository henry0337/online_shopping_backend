package com.henry.online_shopping.controller;

import com.henry.online_shopping.entity.Banner;
import com.henry.online_shopping.service.BannerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/banner")
@Tag(name = "Banner")
public class BannerController {

    private final BannerService service;

    @GetMapping
    public ResponseEntity<List<Banner>> getAll() {
        final var body = service.getAll();
        if (body.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Banner> getById(@PathVariable int id) {
        final var body = service.getById(id);
        return service.existsById(id) ? ResponseEntity.ok(body) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Banner> insert(@NonNull @RequestBody Banner banner) {
        if (service.existsByUri(banner.getUrl())) return ResponseEntity.badRequest().build();
        return new ResponseEntity<>(service.insert(banner), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Banner> update(@PathVariable int id, @RequestBody Banner banner) {
        if (!service.existsById(id)) return ResponseEntity.notFound().build();
        if (service.existsByUri(banner.getUrl())) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(service.update(id, banner));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!service.existsById(id)) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}