/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.Controlador;

import com.example.demo.Modelo.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Modelo.Message;
import com.example.demo.Servicio.MessageService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/**
 *
 * @author USUARIO
 */

@RestController
@RequestMapping("/api/Message")
@CrossOrigin(value = "*")

public class MessageController {
      @Autowired
    private MessageService messageService;
    
    @GetMapping("/all")
    public List<Message> getAll(){
        return messageService.getAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Message> getMessage(@PathVariable("id") int id){
        return messageService.getMessage(id);
    } 
    
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Message save (@RequestBody Message message){
        return messageService.save(message);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Message> actualizarMessage(@PathVariable("id") int id, @RequestBody Message messageReceived) {
        return messageService.actualizarMessage(id, messageReceived);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        messageService.deleteMessage(id);
    }
}
