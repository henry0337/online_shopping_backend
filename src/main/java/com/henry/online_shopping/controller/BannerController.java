package com.henry.online_shopping.controller;

import com.henry.online_shopping.annotation.FasterRestController;
import com.henry.online_shopping.entity.Banner;
import com.henry.online_shopping.service.BannerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@FasterRestController(url = "/api/v1/banner", name = "Banner")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BannerController {

    BannerService service;

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

    @PostMapping
    public ResponseEntity<Banner> insert(@NonNull @RequestBody Banner banner) {
        if (service.existsByUri(banner.getUrl())) return ResponseEntity.badRequest().build();
        return new ResponseEntity<>(service.insert(banner), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Banner> update(@RequestParam int id, @RequestBody Banner banner) {
        if (!service.existsById(id)) return ResponseEntity.notFound().build();
        if (service.existsByUri(banner.getUrl())) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(service.update(id, banner));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id) {
        if (!service.existsById(id)) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}