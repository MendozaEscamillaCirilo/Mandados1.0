package com.mandados.Entidades;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
@Table(name = "Comercios")
public class ComerciosEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String nombre;

	@Column
    private String email;

	@ManyToOne()
	@JoinColumn(name = "tipo_id")
	private Tipos_comerciosEntity tipoComercio;

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
	

	public Tipos_comerciosEntity getTipoComercio() {
		return tipoComercio;
	}

	public void setTipoComercio(Tipos_comerciosEntity tipoComercio) {
		this.tipoComercio = tipoComercio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}