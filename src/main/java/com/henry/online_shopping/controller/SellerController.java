package com.henry.online_shopping.controller;

import com.henry.online_shopping.annotation.FasterRestController;
import com.henry.online_shopping.annotation.OneLineMapping;
import com.henry.online_shopping.entity.Seller;
import com.henry.online_shopping.service.SellerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FasterRestController(url = "/api/v1/seller", name = "Seller")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellerController {

    SellerService service;

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
    public Seller insert(@RequestBody Seller seller) {
        return service.insert(seller);
    }

    @PutMapping
    public ResponseEntity<Seller> update(@RequestParam int id, @RequestBody Seller seller) {
        if (!service.existsById(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(service.update(id, seller));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id) {
        if (!service.existsById(id)) return ResponseEntity.notFound().build();
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
