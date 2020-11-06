package com.mandados.Entidades;

import javax.persistence.*;

@Entity
@Table(name = "Productos")
public class ProductosEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String nombre;
	@Column
	private String contenido;
	@Column
	private String descripcion;
	@Column
	private Double precio;
	@Column 
	private String imagen;
	
	@ManyToOne()
	@JoinColumn(name = "categoria_id")
	private CategoriasEntity categoria;

	@ManyToOne()
	@JoinColumn(name = "comercio_id")
	private ComerciosEntity comercio;

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

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public CategoriasEntity getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriasEntity categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "ProductosEntity [categoria=" + categoria + ", comercio=" + comercio + ", contenido=" + contenido
				+ ", descripcion=" + descripcion + ", id=" + id + ", imagen=" + imagen + ", nombre=" + nombre
				+ ", precio=" + precio + "]";
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public ComerciosEntity getComercio() {
		return comercio;
	}

	public void setComercio(ComerciosEntity comercio) {
		this.comercio = comercio;
	}


	
}