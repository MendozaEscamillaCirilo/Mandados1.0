package com.mandados.Entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Restaurante {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String nombre;
    
    @Column
    private String correo;
    
    @Column
    private String calle;
    
    @Column
    private String colonia;
    
    @Column
    private String numero_casa;
    
    @Column
    private String referencia_domicilio;

	@Override
	public String toString() {
		return "Restaurante [id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", calle=" + calle + ", colonia="
				+ colonia + ", numero_casa=" + numero_casa + ", referencia_domicilio=" + referencia_domicilio + "]";
	}

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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getNumero_casa() {
		return numero_casa;
	}

	public void setNumero_casa(String numero_casa) {
		this.numero_casa = numero_casa;
	}

	public String getReferencia_domicilio() {
		return referencia_domicilio;
	}

	public void setReferencia_domicilio(String referencia_domicilio) {
		this.referencia_domicilio = referencia_domicilio;
	}
    
    
}
