package com.mandados.Entidades;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Set;

@Entity
@Table(name = "Comercios")
public class ComerciosEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nombre;

	@Column
    private String email;

	@ManyToOne()
	@JoinColumn(name = "tipo_id")
	private Tipos_comerciosEntity tipocomercio;

	@OneToMany(mappedBy = "comercio",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<SucursalesEntity> sucursales;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Comercios_Categorias",
            joinColumns = {@JoinColumn(name = "comercio_id")},
            inverseJoinColumns = {@JoinColumn(name = "categoria_id")}
    )
	private Set<CategoriasEntity> categorias;
	
	@OneToMany(mappedBy = "comercio",cascade = CascadeType.ALL, orphanRemoval = true)
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
	

	public Tipos_comerciosEntity getTipoComercio() {
		return tipocomercio;
	}

	public void setTipoComercio(Tipos_comerciosEntity tipocomercio) {
		this.tipocomercio = tipocomercio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ComerciosEntity [categorias=" + categorias + ", email=" + email + ", id=" + id + ", nombre=" + nombre
				+ ", productos=" + productos + ", sucursales=" + sucursales + ", tipocomercio=" + tipocomercio + "]";
	}

	public Set<SucursalesEntity> getSucursales() {
		return sucursales;
	}

	public void setSucursales(Set<SucursalesEntity> sucursales) {
		this.sucursales = sucursales;
	}

	public Set<CategoriasEntity> getCategorias() {
		return categorias;
	}

	public void setCategorias(Set<CategoriasEntity> categorias) {
		this.categorias = categorias;
	}


	public Set<ProductosEntity> getProductos() {
		return productos;
	}

	public void setProductos(Set<ProductosEntity> productos) {
		this.productos = productos;
	}

	
}