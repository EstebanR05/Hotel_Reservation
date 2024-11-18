/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.Servicio;

import com.example.demo.Repositorio.CategoryRepository;

import java.util.List;

import com.example.demo.exception.RecursoNoEncontradoExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.Modelo.Category;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

/**
 * @author USUARIO
 */

@Service

public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.getAll();
    }

    public Optional<Category> getCategory(int id) {
        return categoryRepository.getCategory(id);
    }

    public Category save(Category category) {
        if (category.getId() == null) {
            return categoryRepository.save(category);
        } else {
            Optional<Category> category1 = categoryRepository.getCategory(category.getId());
            if (category1.isEmpty()) {
                return categoryRepository.save(category);
            } else {
                return category;
            }
        }
    }

    public ResponseEntity<Category> actualizarCategory(int id, Category categoryReceived) {
        Optional<Category> category = categoryRepository.getCategory(id);
        if (category.isPresent()) {
            Category categoryToUpdate = category.get();
            categoryToUpdate.setDescription(categoryReceived.getDescription());
            categoryToUpdate.setName(categoryReceived.getName());
            categoryRepository.save(categoryToUpdate);
            return ResponseEntity.ok(categoryToUpdate);
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
    }

    public void deleteCategory(int id) {
        if (categoryRepository.getCategory(id).isPresent()) {
            categoryRepository.deleteCategoryById(id);
            System.out.println("Eliminado");
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
    }
}
