package com.mandados.Entidades;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
// import javax.validation.constraints.NotBlank;
// import javax.validation.constraints.NotNull;
// import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

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
	
}