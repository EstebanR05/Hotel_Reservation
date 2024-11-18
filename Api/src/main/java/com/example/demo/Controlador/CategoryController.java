/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.Controlador;

import com.example.demo.Servicio.CategoryService;

import java.util.List;

import com.example.demo.Modelo.Category;

import java.util.Optional;

import com.example.demo.exception.RecursoNoEncontradoExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author USUARIO
 */

@RestController
@RequestMapping("/api/Category")
@CrossOrigin(value = "*")

public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Category> getCategory(@PathVariable("id") int id) {
        return categoryService.getCategory(id);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Category save(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Category> actualizarCategoria(@PathVariable("id") int id, @RequestBody Category categoryReceived) {
        return categoryService.actualizarCategory(id, categoryReceived);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        categoryService.deleteCategory(id);
    }
}
