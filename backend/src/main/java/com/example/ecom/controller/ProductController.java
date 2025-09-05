package com.example.ecom.controller;

import com.example.ecom.model.Product;
import com.example.ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository repo;

    @GetMapping
    public Page<Product> list(@RequestParam(value="q", required=false) String q,
                              @RequestParam(value="page", defaultValue="0") int page,
                              @RequestParam(value="size", defaultValue="12") int size) {
        Pageable p = PageRequest.of(page, size, Sort.by("id").descending());
        if (q == null || q.isBlank()) return repo.findAll(p);
        return repo.search(q, p);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
