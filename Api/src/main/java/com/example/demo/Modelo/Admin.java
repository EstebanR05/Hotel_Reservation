package com.example.demo.Modelo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "admin")
public class Admin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAdmin;
    private String email;
    private String name;
    private String password;

    public Admin(Integer idAdmin, String email, String name, String password) {
        this.idAdmin = idAdmin;
        this.email = email;
        this.name = name;
        this.password = password;


    }

    public Integer getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Integer idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
