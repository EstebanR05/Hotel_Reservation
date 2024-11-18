/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.Repositorio;

import com.example.demo.Interface.RoomInterface;
import org.springframework.stereotype.Repository;
import com.example.demo.Modelo.Room;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author USUARIO
 */

@Repository

public class RoomRepository {
     @Autowired
    private RoomInterface roomCrud;
    
    public List<Room> getAll(){
        return (List<Room>) roomCrud.findAll();
    }
    
    public Optional<Room> getRoom(int id){
        return roomCrud.findById(id);
    }
    
    public Room save(Room room){
        return roomCrud.save(room);
    }

    public void deleteRoom(int id){
         roomCrud.deleteById(id);
    }
}
