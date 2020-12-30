package com.mandados.Entidades;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Repartidores")
public class RepartidoresEntity {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @Column
	private String nombre;
	@Column
	private String primerapellido;
	@Column
	private String segundoapellido;
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
    @Column
	private String estatus;

    @OneToMany(mappedBy = "repartidor",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<PedidosEntity> pedidos;

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

    public Set<PedidosEntity> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<PedidosEntity> pedidos) {
        this.pedidos = pedidos;
    }

    public String toString() {
        return "RepartidoresEntity [calle=" + calle + ", colonia=" + colonia + ", email=" + email + ", id=" + id
                + ", municipio=" + municipio + ", nombre=" + nombre + ", numero=" + numero + ", pedidos=" + pedidos
                + ", primerapellido=" + primerapellido + ", segundoapellido=" + segundoapellido + ", telefono="
                + telefono + "]";
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

}
