package com.mandados.Entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Repartidor {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@Column
	@NotNull(message = "Debes especificar el nombre")
    private String nombre;
    
	@Column
	@NotNull(message = "Debes especificar un correo electronico")
    private String correo;
    
	@Column
	@NotNull(message = "Debes especificar el telefono")
    private String telefono;

	@Override
	public String toString() {
		return "Repartidor [id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", telefono=" + telefono + "]";
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
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
