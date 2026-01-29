package com.delivery.deliveryapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.delivery.deliveryapi.entity.Product;
import com.delivery.deliveryapi.repository.ProductRepository;
import com.delivery.deliveryapi.repository.RestaurantRepository;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final RestaurantRepository restaurantRepo;

    public ProductService(ProductRepository repo, RestaurantRepository restaurantRepo) {
        this.repo = repo;
        this.restaurantRepo = restaurantRepo;
    }

    public List<Product> list(Long restaurantId) {
        if (restaurantId != null) return repo.findByRestaurantId(restaurantId);
        return repo.findAll();
    }

    public Product create(Product p) {
        var restaurant = restaurantRepo.findById(p.getRestaurant().getId()).orElseThrow();
        p.setRestaurant(restaurant);
        if (p.getAvailable() == null) p.setAvailable(true);
        return repo.save(p);
    }

    // Si se necesita actualizar la imagen de un producto
    public Product updateImage(Long id, String base64Image) {
        Product product = repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setImageBase64(base64Image);
        return repo.save(product);
    }
}
