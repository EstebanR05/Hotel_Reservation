/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.Controlador;

import com.example.demo.Modelo.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Modelo.Room;
import com.example.demo.Servicio.RoomService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/api/Room")
@CrossOrigin(value = "*")

public class RoomController {
      @Autowired
    private RoomService roomService;
    
    @GetMapping("/all")
    public List<Room> getAll(){
        return roomService.getAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Room> getTool(@PathVariable("id") int id){
        return roomService.getRoom(id);
    } 
    
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Room save (@RequestBody Room room){
        return roomService.save(room);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Room> actualizarRoom(@PathVariable("id") int id, @RequestBody Room roomReceived) {
        return roomService.actualizarRoom(id, roomReceived);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        roomService.deleteRoom(id);
    }
}
