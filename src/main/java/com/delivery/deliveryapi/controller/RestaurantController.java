package com.delivery.deliveryapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;  // Importación de PathVariable
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;  // Método para actualizar imagen
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.deliveryapi.entity.Restaurant;
import com.delivery.deliveryapi.service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping
    public List<Restaurant> list() {
        return service.list();
    }

    @PostMapping
    public Restaurant create(@RequestBody Restaurant r) {
        return service.create(r);
    }

    @PutMapping("/{id}/image")
    public Restaurant updateImage(@PathVariable Long id, @RequestBody String base64Image) {
        return service.updateImage(id, base64Image);
    }
}
