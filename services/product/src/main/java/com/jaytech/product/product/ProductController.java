package com.jaytech.product.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Integer> createProduct(
            @RequestBody @Valid ProductRequest request
    ){
        return ResponseEntity.ok(service.createProduct(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProduct(
            @RequestBody List<ProductPurchaseRequest> request
    ){
        return ResponseEntity.ok(service.purchaseProducts(request));
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<ProductRessponse> findById(
            @PathVariable("product_id") Integer productId
    ){
        return ResponseEntity.ok(service.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductRessponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

}
