/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.Servicio;

import com.example.demo.Modelo.Category;
import com.example.demo.Repositorio.ClientRepository;
import java.util.List;
import java.util.Optional;

import com.example.demo.exception.RecursoNoEncontradoExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.Modelo.Client;
/**
 *
 * @author USUARIO
 */

@Service

public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    
    public List<Client> getAll(){
        return clientRepository.getAll();
    }
    
    public Optional<Client> getClient(int id){
        return clientRepository.getClient(id);
    }
    
     public Client save (Client client){
        if (client.getIdClient() == null){
            return clientRepository.save(client);
        } else {
            Optional<Client> client1 = clientRepository.getClient(client.getIdClient());
            if(client1.isEmpty()){
                return clientRepository.save(client);
            } else {
                return client;
            }
        }
    }

    public ResponseEntity<Client> actualizarClient(int id, Client clientReceived) {
        Optional<Client> client = clientRepository.getClient(id);
        if (client.isPresent()) {
            Client clientToUpdate = client.get();
            clientToUpdate.setAge(clientReceived.getAge());
            clientToUpdate.setEmail(clientReceived.getEmail());
            clientToUpdate.setName(clientReceived.getName());
            clientToUpdate.setPassword(clientReceived.getPassword());
            clientRepository.save(clientToUpdate);
            return ResponseEntity.ok(clientToUpdate);
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
    }
    public void deleteClient(int id) {
        if (clientRepository.getClient(id).isPresent()) {
            clientRepository.deleteClient(id);
            System.out.println("Eliminado");
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
    }
}
