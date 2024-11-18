package com.example.demo.Repositorio;

import com.example.demo.Interface.AdminInterface;
import com.example.demo.Interface.ClientInterface;
import com.example.demo.Modelo.Admin;
import com.example.demo.Modelo.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AdminRepositorio {

private AdminInterface adminInterface;


    @Autowired
    private AdminInterface extencionesCrud;

    public List<Admin> getAll(){
        return (List<Admin>) extencionesCrud.findAll();
    }

//    public Optional<Client> getClient(int id){
//        return extencionesCrud.findById(id);
//    }
//
//    public Client save(Client client){
//        return extencionesCrud.save(client);
//    }
}
