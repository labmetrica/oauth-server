package com.metrica.formacion.oauthserver.entity;

import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Entity
@Table(name = "usuarios")
public class usuarios {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Apellido")
    private String apellido;

    @Column(name = "username",unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "grupo")
    private int grupo;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "createdAT")
    private LocalDateTime createdAT;

    @Column(name = "activo")
    private boolean activo;

    @Column(name = "ultimaModificacion")
    private LocalDateTime ultimaModificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private tipos tipo = tipos.Empleado;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "ID"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<roles> roles;

    public enum tipos {Empleado, LAB}

    ;

    //Constructor

    public usuarios() {
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAT() {
        return createdAT;
    }

    @PrePersist
    public void setCreatedAT() {
        this.createdAT = LocalDateTime.now();
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getUltimaModificacion() {
        return ultimaModificacion;
    }

    @PreUpdate
    public void setUltimaModificacion() {
        this.ultimaModificacion = LocalDateTime.now();
    }

    public void setUltima_modificacion() {

        this.ultimaModificacion = LocalDateTime.now();
    }

    public tipos getTipo() {
        return tipo;
    }

    public void setTipo(tipos tipo) {
        this.tipo = tipo;
    }

    public List<roles> getRoles() {
        return roles;
    }

    public void setRoles(List<roles> roles) {
        this.roles = roles;
    }
}
