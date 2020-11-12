package com.mandados.Entidades;

import javax.persistence.*;

@Entity
@Table(name = "Sucursales")
public class SucursalesEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String codigopostal;
    @Column
    private String nombre;
	@Column
    private String calle;
    @Column
    private String numero;
    @Column
    private String colonia;
    @Column
    private String municipio;
    @Column
    private String telefono;
    @Column
    private String email;

	@ManyToOne()
	@JoinColumn(name = "comercio_id")
    private ComerciosEntity comercio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ComerciosEntity getComercio() {
        return comercio;
    }

    public void setComercio(ComerciosEntity comercio) {
        this.comercio = comercio;
    }

    public String toString() {
        return "SucursalesEntity [calle=" + calle + ", codigopostal=" + codigopostal + ", colonia=" + colonia
                + ", comercio=" + comercio + ", email=" + email + ", id=" + id + ", municipio=" + municipio
                + ", nombre=" + nombre + ", numero=" + numero + ", telefono=" + telefono + "]";
    }
    
}
