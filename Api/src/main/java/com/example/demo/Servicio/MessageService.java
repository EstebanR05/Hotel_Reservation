/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.Servicio;

import com.example.demo.Modelo.Category;
import com.example.demo.Repositorio.MessageRepository;
import com.example.demo.exception.RecursoNoEncontradoExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.Modelo.Message;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author USUARIO
 */

@Service

public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    
    public List<Message> getAll(){
        return messageRepository.getAll();
    }
    
    public Optional<Message> getMessage(int id){
        return messageRepository.getMessage(id);
    }
    
    public Message save (Message message){
        if (message.getIdMessage() == null){
            return messageRepository.save(message);
        } else {
            Optional<Message> message1 = messageRepository.getMessage(message.getIdMessage());
            if(message1.isEmpty()){
                return messageRepository.save(message);
            } else {
                return message;
            }
        }
    }
    public ResponseEntity<Message> actualizarMessage(int id, Message messageReceived) {
        Optional<Message> message = messageRepository.getMessage(id);
        if (message.isPresent()) {
            Message messageToUpdate = message.get();
            messageToUpdate.setMessageText(messageReceived.getMessageText());
            messageToUpdate.setClient(messageReceived.getClient());
            messageToUpdate.setTool(messageReceived.getTool());
            messageRepository.save(messageToUpdate);
            return ResponseEntity.ok(messageToUpdate);
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
    }

    public void deleteMessage(int id) {
        if (messageRepository.getMessage(id).isPresent()) {
            messageRepository.deleteMessage(id);
            System.out.println("Eliminado");
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
    }
}
