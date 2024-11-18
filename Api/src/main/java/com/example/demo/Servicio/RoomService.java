/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.Servicio;

import com.example.demo.Modelo.Category;
import com.example.demo.Repositorio.RoomRepository;
import java.util.List;
import java.util.Optional;

import com.example.demo.exception.RecursoNoEncontradoExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.Modelo.Room;
/**
 *
 * @author USUARIO
 */

@Service

public class RoomService {
      @Autowired
    private RoomRepository roomRepository;
    
    public List<Room> getAll(){
        return roomRepository.getAll();
    }
    
    public Optional<Room> getRoom(int id){
        return roomRepository.getRoom(id);
    }
    
    public Room save (Room room){
        if (room.getId() == null){
            return roomRepository.save(room);
        } else {
            Optional<Room> room1 = roomRepository.getRoom(room.getId());
            if(room1.isEmpty()){
                return roomRepository.save(room);
            } else {
                return room;
            }
        }
    }
    public ResponseEntity<Room> actualizarRoom(int id, Room roomReceived) {
        Optional<Room> room = roomRepository.getRoom(id);
        if (room.isPresent()) {
            Room roomToUpdate = room.get();
            roomToUpdate.setDescription(roomReceived.getDescription());
            roomToUpdate.setHotel(roomReceived.getHotel());
            roomToUpdate.setName(roomReceived.getName());
            roomToUpdate.setStars(roomReceived.getStars());
            roomToUpdate.setCategory(roomReceived.getCategory());
            roomRepository.save(roomToUpdate);
            return ResponseEntity.ok(roomToUpdate);
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
    }

    public void deleteRoom(int id) {
        if (roomRepository.getRoom(id).isPresent()) {
            roomRepository.deleteRoom(id);
            System.out.println("Eliminado");
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
    }
}
