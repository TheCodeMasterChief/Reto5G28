/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.Servicio;

import com.example.demo.Modelo.Reservation;
import com.example.demo.Repositorio.CountClient;
import com.example.demo.Repositorio.ReservationRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author @CjVm93
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
    }else{
    Optional<Reservation> reservation1 = reservationRepository.getReservation(reservation.getIdReservation());
    if(reservation1.isEmpty()){
        return reservationRepository.save(reservation);
    }else{
        return reservation;
    }
}
}
    public Reservation update(Reservation reservation){
        if(reservation.getIdReservation()!=null){
            Optional<Reservation> e= reservationRepository.getReservation(reservation.getIdReservation());
            if(!e.isEmpty()){
                if(reservation.getStartDate()!=null){ e.get().setStartDate(reservation.getStartDate());
                }
                if(reservation.getDevolutionDate()!=null){ e.get().setDevolutionDate(reservation.getDevolutionDate());
                }
                if(reservation.getStatus()!=null){
                    e.get().setStatus(reservation.getStatus());
                }
                reservationRepository.save(e.get());
                return e.get();
            }else{
                return reservation;
            }
        }else{
            return reservation; 
        } 
    }
    
    public boolean deleteReservation(int id){
        Boolean d = getReservation(id).map(reservation ->{
            reservationRepository.delete(reservation);
            return true;
            
        }).orElse(false);
        return d;
    }
    
    ///////////////////////////////////Reto5///////////////////////////////////
    
    public Status getReservationStatusReport(){
        List<Reservation>completed=reservationRepository.getReservationByStatus("completed");
        List<Reservation>cancelled=reservationRepository.getReservationByStatus("cancelled");
        return new Status (completed.size(),cancelled.size());
    }
    public List<Reservation> informePeriodoTiempoReservas(String datoA, String datoB){
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date a = new Date();
        Date b = new Date();
        
        try{
            a = parser.parse(datoA);
            b = parser.parse(datoB);
        }catch(ParseException e){
            e.printStackTrace();
        }
        if (a.before(b)){
            return reservationRepository.informePeriodoTiempoReservas(a, b);
        }else{
            return new ArrayList<>();
        }
        
    }
    
    public List<CountClient> getTopClients(){
        return reservationRepository.getTopClient();
    }
    
    
}

