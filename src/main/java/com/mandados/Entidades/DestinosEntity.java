package com.mandados.Entidades;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Destinos")
public class DestinosEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
	private String nombres;
	@Column
	private String primerapellido;
	@Column
	private String segundoapellido;
	@Column
    private String calle;
    @Column
    private int numero;
    @Column
    private String colonia;
    @Column
    private String municipio;
    @Column
    private String telefono;

    @OneToMany(mappedBy = "destino",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<PedidosEntity> pedidos;

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

    public String getPrimerapellido() {
        return primerapellido;
    }

    public void setPrimerapellido(String primerapellido) {
        this.primerapellido = primerapellido;
    }

    public String getSegundoapellido() {
        return segundoapellido;
    }

    public void setSegundoapellido(String segundoapellido) {
        this.segundoapellido = segundoapellido;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
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

    public Set<PedidosEntity> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<PedidosEntity> pedidos) {
        this.pedidos = pedidos;
    }

    public String toString() {
        return "DestinosEntity [calle=" + calle + ", colonia=" + colonia + ", id=" + id + ", municipio=" + municipio
                + ", nombres=" + nombres + ", numero=" + numero + ", pedidos=" + pedidos + ", primerapellido="
                + primerapellido + ", segundoapellido=" + segundoapellido + ", telefono=" + telefono + "]";
    }

    public DestinosEntity(String nombres, String primerapellido, String segundoapellido, String calle, int numero,
            String colonia, String municipio, String telefono) {
        this.nombres = nombres;
        this.primerapellido = primerapellido;
        this.segundoapellido = segundoapellido;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.municipio = municipio;
        this.telefono = telefono;
    }

    public DestinosEntity() {
    }

}
