package com.fooddelivery.src.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @GetMapping("/")
    public String home() {
        return "Welcome to Peggy's delivery app version 3";
    }
}
