package com.mandados.Entidades;
import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "Categorias")
public class CategoriasEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
	@Column 
	private String nombre;

	@Column
    private String descripcion;
	
	@OneToMany(mappedBy = "categoria",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProductosEntity> productos;

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

	public Set<ProductosEntity> getProductos() {
		return productos;
	}

	public void setProductos(Set<ProductosEntity> productos) {
		this.productos = productos;
	}

	@Override
	public String toString() {
		return "CategoriasEntity [descripcion=" + descripcion + ", id=" + id + ", nombre=" + nombre + ", productos="
				+ productos + "]";
	}
	
	
}
