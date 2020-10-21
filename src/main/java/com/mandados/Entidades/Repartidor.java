package com.mandados.Entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Repartidor {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String nombre;
    
    @Column
    private String correo;
    
    @Column
    private String telefono;

	@Override
	public String toString() {
		return "Restaurante [id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", colonia=" + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return correo;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
