package com.mandados.Entidades;

import javax.persistence.*;

@Entity
@Table(name = "Productos")
public class ProductosEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String nombre;
	@Column
	private String contenido;
	@Column
	private String descripcion;
	@Column
    private Double precio;
	
	
}