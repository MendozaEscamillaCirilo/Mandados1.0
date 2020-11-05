package com.mandados.Entidades;

import javax.persistence.*;

@Entity
@Table(name = "Destinos")
public class DestinosEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
	private String nombres;
	@Column
	private String primer_apellido;
	@Column
	private String segundo_apellido;
	@Column
    private Double calle;
    @Column
    private String numero;
    @Column
    private String colonia;
    @Column
    private String municipio;
    @Column
    private String telefono;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPrimer_apellido() {
        return primer_apellido;
    }

    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    public Double getCalle() {
        return calle;
    }

    public void setCalle(Double calle) {
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

    @Override
    public String toString() {
        return "DestinosEntity [calle=" + calle + ", colonia=" + colonia + ", id=" + id + ", municipio=" + municipio
                + ", nombres=" + nombres + ", numero=" + numero + ", primer_apellido=" + primer_apellido
                + ", segundo_apellido=" + segundo_apellido + ", telefono=" + telefono + "]";
    }

}
