package com.delivery.deliveryapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.deliveryapi.entity.Product;
import com.delivery.deliveryapi.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> list(@RequestParam(required = false) Long restaurantId) {
        return service.list(restaurantId);
    }

    @PostMapping
    public Product create(@RequestBody Product p) {
        return service.create(p);
    }

    // Nuevo endpoint para actualizar la imagen en base64 de un producto
    @PutMapping("/{id}/image")
    public Product updateImage(@PathVariable Long id, @RequestBody String base64Image) {
        return service.updateImage(id, base64Image);
    }
}
