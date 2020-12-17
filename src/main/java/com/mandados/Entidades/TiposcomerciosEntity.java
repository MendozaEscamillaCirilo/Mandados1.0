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
public class TiposcomerciosEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column 
	private String nombre;

	@Column
	private String descripcion;

	@OneToMany(mappedBy = "tipocomercio",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ComerciosEntity> comercios;

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

	public String toString() {
		return "Tipos_comerciosEntity [comercios=" + comercios + ", descripcion=" + descripcion + ", id=" + id
				+ ", nombre=" + nombre + "]";
	}

}