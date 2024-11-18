package com.example.demo.Interface;

import com.example.demo.Modelo.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminInterface extends CrudRepository<Admin, Integer> {
}
