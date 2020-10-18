package com.mandados.Entidades;

import javax.persistence.*;

@Entity
@Table(name = "productos")
public class ProductosEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// private String nombre;
	
	
	
}