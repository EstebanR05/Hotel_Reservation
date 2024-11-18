/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.Servicio;

import com.example.demo.Repositorio.ReservationRepository;
import java.util.List;

import com.example.demo.exception.RecursoNoEncontradoExcepcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.Modelo.Reservation;
import java.util.Optional;
/**
 *
 * @author USUARIO
 */

@Service

public class ReservationService {
       @Autowired
    private ReservationRepository reservationRepository;
    
    public List<Reservation> getAll(){
        return reservationRepository.getAll();
    }
    
    public Optional<Reservation> getReservation(int id){
        return reservationRepository.getReservation(id);
    }

    public Reservation save (Reservation reservation){
        if (reservation.getIdReservation() == null){
            return reservationRepository.save(reservation);
        } else {
            Optional<Reservation> reservation1 = reservationRepository.getReservation(reservation.getIdReservation());
            if(reservation1.isEmpty()){
                return reservationRepository.save(reservation);
            } else {
                return reservation;
            }
        }
    }
    public ResponseEntity<Reservation> actualizarReservation(int id, Reservation reservationReceived) {
        Optional<Reservation> reservation = reservationRepository.getReservation(id);
        if (reservation.isPresent()) {
            Reservation reservationToUpdate = reservation.get();
            reservationToUpdate.setDevolutionDate(reservationReceived.getDevolutionDate());
            reservationToUpdate.setScore(reservationReceived.getScore());
            reservationToUpdate.setStartDate(reservationReceived.getStartDate());
            reservationToUpdate.setStatus(reservationReceived.getStatus());
            reservationToUpdate.setClient(reservationReceived.getClient());
            reservationToUpdate.setRoom(reservationReceived.getRoom());
            reservationRepository.save(reservationToUpdate);
            return ResponseEntity.ok(reservationToUpdate);
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
    }

    public void deleteReservation(int id) {
        if (reservationRepository.getReservation(id).isPresent()) {
            reservationRepository.deleteMessage(id);
            System.out.println("Eliminado");
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
    }
}
