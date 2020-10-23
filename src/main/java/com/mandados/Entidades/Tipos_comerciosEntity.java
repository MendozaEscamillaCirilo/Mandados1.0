package com.mandados.Entidades;

import javax.persistence.*;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
@Entity
@Table(name = "Tipos_comercios")
public class Tipos_comerciosEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column 
	private String nombre;

	@Column
	private String descripcion;

	@OneToMany(mappedBy = "tipoComercio",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ComerciosEntity> comercios;

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<ComerciosEntity> getComercios() {
		return comercios;
	}

	public void setComercios(Set<ComerciosEntity> comercios) {
		this.comercios = comercios;
	}
	
}