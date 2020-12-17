package com.mandados.Entidades;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.util.List;
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
	private TiposcomerciosEntity tipocomercio;

	@OneToMany(mappedBy = "comercio",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SucursalesEntity> sucursales;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Comercios_Categorias",
            joinColumns = {@JoinColumn(name = "comercio_id")},
            inverseJoinColumns = {@JoinColumn(name = "categoria_id")}
    )
	private Set<CategoriasEntity> categorias;
	
	@OneToMany(mappedBy = "comercio",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProductosEntity> productos;

	@Column
	private Boolean estatus;

	@Column
	private java.sql.Time horaapertura;
	
	@Column
    private java.sql.Time horacierre;

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
	

	public TiposcomerciosEntity getTipocomercio() {
		return tipocomercio;
	}

	public void setTipocomercio(TiposcomerciosEntity tipocomercio) {
		this.tipocomercio = tipocomercio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<SucursalesEntity> getSucursales() {
		return sucursales;
	}

	public void setSucursales(List<SucursalesEntity> sucursales) {
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

	public String toString() {
		return "Comercio => id = " + id + ", nombre = " + nombre + ", email = " + email;
	}

	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}

	public java.sql.Time getHoraapertura() {
		return horaapertura;
	}

	public void setHoraapertura(java.sql.Time horaapertura) {
		this.horaapertura = horaapertura;
	}

	public java.sql.Time getHoracierre() {
		return horacierre;
	}

	public void setHoraCierre(java.sql.Time horacierre) {
		this.horacierre = horacierre;
	}
}